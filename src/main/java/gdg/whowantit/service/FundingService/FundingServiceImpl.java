package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.FundingConverter;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRelationResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.dto.kakaoPayDto.KakaoPayResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.*;
import gdg.whowantit.service.ImageService.ImageService;
import gdg.whowantit.util.SecurityUtil;
import gdg.whowantit.util.StringListUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundingServiceImpl implements FundingService{
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final FundingRelationRepository fundingRelationRepository;
    private final SponsorRepository sponsorRepository;
    private final FundingScrapRepository fundingScrapRepository;
    private final ImageService imageService;
    private final KakaoPayProperties payProperties;
    private final RestTemplate restTemplate =new RestTemplate();
    private KakaoPayResponseDto.KakaoReadyResponse kakaoReady;
    private final KakaopayService kakaopayService;
    @Override
    @Transactional
    public FundingResponseDto.createResponse createFunding(FundingRequestDto.createRequest request){

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Beneficiary beneficiary=beneficiaryRepository.findByBeneficiaryId(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Funding funding= Funding.builder()
                .title(request.getTitle())
                .productName(request.getProduct_name())
                .targetAmount(request.getTarget_amount())
                .content(request.getText())
                .deadline(request.getDeadline())
                .beneficiary(beneficiary)
                .build();

        fundingRepository.save(funding);

        return FundingResponseDto.createResponse.builder()
                .fundingId(funding.getFundingId())
                .title(funding.getTitle())
                .product_name(funding.getProductName())
                .target_amount(funding.getTargetAmount())
                .text(funding.getContent())
                .attachedImage(funding.getAttachedImage())
                .status(String.valueOf(funding.getStatus()))
                .approval_status(String.valueOf(funding.getApprovalStatus()))
                .deadline(funding.getDeadline())
                .beneficiaryId(funding.getBeneficiary().getBeneficiaryId())
                .build();
    }

    public FundingResponseDto.createResponse createFundingImage(Long fundingId, List<MultipartFile> images){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Beneficiary beneficiary=beneficiaryRepository.findByBeneficiaryId(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Funding funding=fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        if (!images.isEmpty()) {
            List<String> imageUrls = imageService.uploadMultipleImages("fundings", images);
            funding.setAttachedImage(StringListUtil.listToString(imageUrls));
        }

        fundingRepository.save(funding);

        return FundingResponseDto.createResponse.builder()
                .fundingId(funding.getFundingId())
                .title(funding.getTitle())
                .product_name(funding.getProductName())
                .target_amount(funding.getTargetAmount())
                .text(funding.getContent())
                .attachedImage(funding.getAttachedImage())
                .status(String.valueOf(funding.getStatus()))
                .approval_status(String.valueOf(funding.getApprovalStatus()))
                .deadline(funding.getDeadline())
                .beneficiaryId(funding.getBeneficiary().getBeneficiaryId())
                .build();
    }

    public FundingResponseDto.createResponse updateFunding(Long fundingId, boolean permission){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.ADMIN){
            throw new TempHandler(ErrorStatus.PERMISSION_DENIED);
        }
        Funding funding=fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        if(permission){
            funding.setApprovalStatus(ApprovalStatus.APPROVED);
        }
        else{
            funding.setApprovalStatus(ApprovalStatus.DISAPPROVED);
        }
        fundingRepository.save(funding);

        return FundingResponseDto.createResponse.builder()
                .fundingId(funding.getFundingId())
                .title(funding.getTitle())
                .product_name(funding.getProductName())
                .target_amount(funding.getTargetAmount())
                .text(funding.getContent())
                .status(String.valueOf(funding.getStatus()))
                .approval_status(String.valueOf(funding.getApprovalStatus()))
                .deadline(funding.getDeadline())
                .beneficiaryId(funding.getBeneficiary().getBeneficiaryId())
                .build();


    }

    public List<BeneficiaryResponseDto.fundingResponse> getFundingList(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Funding> fundingList = fundingRepository.findAllByOrderByFundingIdDesc();
        return fundingList.stream()
                .map(FundingConverter::toFundingResponse)
                .collect(Collectors.toList());

    }

    public List<BeneficiaryResponseDto.fundingResponse> getFundingList(Status status){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Funding> fundingList = fundingRepository.findAllByStatusOrderByFundingIdDesc(status);
        return fundingList.stream()
                .map(FundingConverter::toFundingResponse)
                .collect(Collectors.toList());
    }

    public FundingResponseDto.infoResponse getFundingInfo(Long fundingId){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));
        return FundingConverter.toInfoResponse(funding);


    }

    public List<FundingResponseDto.sponsorResponse> getSponsorList(Long fundingId){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        List<FundingRelation> fundingRelations=fundingRelationRepository.findAllByFunding_FundingIdOrderByFundingRelationIdDesc(funding.getFundingId());
        return fundingRelations.stream()
                .map(FundingConverter::toSponsorResponse)
                .collect(Collectors.toList());
    }

    public FundingResponseDto.scrapResponse scrapFunding(Long fundingId){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Sponsor sponsor=sponsorRepository.findById(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.SPONSOR){
            throw new TempHandler(ErrorStatus.FUNDING_PERMISSION_DENIED);
        }
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        if(fundingScrapRepository.findBySponsor_SponsorIdAndFunding_FundingId(user.getId(),fundingId)!=null)
        {
            throw new TempHandler(ErrorStatus.FUNDING_ALREADY_SCRAPPED);
        }

        FundingScrap fundingScrap=FundingScrap.builder()
                .sponsor(sponsor)
                .funding(funding)
                .build();

        fundingScrapRepository.save(fundingScrap);

        return FundingResponseDto.scrapResponse.builder()
                .fundingId(fundingScrap.getFunding().getFundingId())
                .sponsorId(fundingScrap.getSponsor().getUser().getId())
                .scrapId(fundingScrap.getFundingScrapId())
                .build();
    }

    public void deleteScrapFunding(Long fundingId){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.SPONSOR){
            throw new TempHandler(ErrorStatus.FUNDING_PERMISSION_DENIED);
        }
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        if(fundingScrapRepository.findBySponsor_SponsorIdAndFunding_FundingId(user.getId(),fundingId)==null)
        {
            throw new TempHandler(ErrorStatus.FUNDING_SCRAP_NOT_FOUND);
        }

        FundingScrap fundingScrap=fundingScrapRepository.findBySponsor_SponsorIdAndFunding_FundingId(user.getId(),fundingId);
        fundingScrapRepository.delete(fundingScrap);

    }

    public KakaoPayResponseDto.KakaoReadyResponse createSpon(Long fundingId, float paymentAmount){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Sponsor sponsor=sponsorRepository.findById(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("partner_order_id", "ORDER_ID");
        parameters.put("partner_user_id","USER_ID");
        parameters.put("item_name", "ITEM_NAME");
        parameters.put("quantity", "1");
        parameters.put("total_amount", paymentAmount);
        parameters.put("vat_amount", "100");
        parameters.put("tax_free_amount", "0");
        parameters.put("approval_url", "http://13.209.33.88:8080/success"); //url 주소 수정 필요
        parameters.put("cancel_url", "http://13.209.33.88:8080/cancel"); //url 주소 수정 필요
        parameters.put("fail_url", "http://13.209.33.88:8080/fail");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, kakaopayService.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoPayResponseDto.KakaoReadyResponse.class);

        FundingRelation fundingRelation=FundingRelation.builder()
                .sponsor(sponsor)
                .funding(funding)
                .paymentAmount(paymentAmount)
                .beneficiary(funding.getBeneficiary())
                .tid(kakaoReady.getTid())
                .paymentStatus(PaymentStatus.READY)
                .build();
        fundingRelationRepository.save(fundingRelation);


        funding.setCurrentAmount(funding.getCurrentAmount() + paymentAmount);
        fundingRepository.save(funding);
        return kakaoReady;

    }

}
