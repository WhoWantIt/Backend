package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.FundingConverter;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.FundingRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundingServiceImpl implements FundingService{
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final BeneficiaryRepository beneficiaryRepository;
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

}
