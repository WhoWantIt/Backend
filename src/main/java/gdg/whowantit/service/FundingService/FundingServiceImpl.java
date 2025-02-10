package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Funding;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.FundingRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

}
