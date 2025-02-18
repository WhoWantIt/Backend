package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.FundingConverter;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRelationResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.*;
import gdg.whowantit.service.ImageService.ImageService;
import gdg.whowantit.util.SecurityUtil;
import gdg.whowantit.util.StringListUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
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

        List<Funding> fundingList = fundingRepository.findAll();
        return fundingList.stream()
                .map(FundingConverter::toFundingResponse)
                .collect(Collectors.toList());

    }

    public List<BeneficiaryResponseDto.fundingResponse> getFundingList(Status status){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Funding> fundingList = fundingRepository.findAllByStatus(status);
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

        List<FundingRelation> fundingRelations=fundingRelationRepository.findAllByFunding_FundingId(funding.getFundingId());
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

    public FundingRelationResponseDto.createResponse createSpon(Long fundingId, float paymentAmount){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Sponsor sponsor=sponsorRepository.findById(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Funding funding = fundingRepository.findById(fundingId)
                .orElseThrow(()->new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));

        FundingRelation fundingRelation=FundingRelation.builder()
                .sponsor(sponsor)
                .funding(funding)
                .paymentAmount(paymentAmount)
                .beneficiary(funding.getBeneficiary())
                .build();
        fundingRelationRepository.save(fundingRelation);

        funding.setCurrentAmount(funding.getCurrentAmount() + paymentAmount);
        fundingRepository.save(funding);

        return FundingRelationResponseDto.createResponse.builder()
                .sponsorId(sponsor.getUser().getId())
                .fundingId(fundingId)
                .paymentAmount(paymentAmount)
                .build();
    }

}
