package gdg.whowantit.converter;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Funding;
import gdg.whowantit.entity.Status;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FundingConverter {
    public static BeneficiaryResponseDto.fundingResponse toFundingResponse(Funding funding){
        LocalDate deadline = funding.getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        // null 체크 후 기본값 설정
        float targetAmount = (funding.getTargetAmount() != null) ? funding.getTargetAmount() : 1.0f; // 0으로 설정하면 나누기 에러 발생 가능
        float currentAmount = (funding.getCurrentAmount() != null) ? funding.getCurrentAmount() : 0.0f;

        // 목표 금액이 0이면 퍼센트 계산을 방지 (기본값 0%)
        double attainmentPercent = (targetAmount > 0) ? (currentAmount / targetAmount * 100.0) : 0.0;

        return BeneficiaryResponseDto.fundingResponse.builder()
                .fundingId(funding.getFundingId())
                .title(funding.getTitle())
                .attachedImage(funding.getAttachedImage())
                .dDay(dDayString)
                .attainmentPercent(attainmentPercent)
                .beneficiaryId(funding.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(funding.getBeneficiary().getUser().getName())
                .beneficiaryNickname(funding.getBeneficiary().getUser().getNickname())
                .build();
    }

    public static FundingResponseDto.infoResponse toInfoResponse(Funding funding){
        // null 체크 후 기본값 설정
        float targetAmount = (funding.getTargetAmount() != null) ? funding.getTargetAmount() : 1.0f; // 0으로 설정하면 나누기 에러 발생 가능
        float currentAmount = (funding.getCurrentAmount() != null) ? funding.getCurrentAmount() : 0.0f;

        // 목표 금액이 0이면 퍼센트 계산을 방지 (기본값 0%)
        double attainmentPercent = (targetAmount > 0) ? (currentAmount / targetAmount * 100.0) : 0.0;

        return FundingResponseDto.infoResponse.builder()
                .fundingId(funding.getFundingId())
                .title(funding.getTitle())
                .content(funding.getContent())
                .attachedImage(funding.getAttachedImage())
                .productName(funding.getProductName())
                .currentAmount(funding.getCurrentAmount())
                .attainmentPercent(attainmentPercent)
                .status(funding.getStatus())
                .approvalStatus(funding.getApprovalStatus())
                .beneficiaryId(funding.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(funding.getBeneficiary().getUser().getName())
                .beneficiaryNickname(funding.getBeneficiary().getUser().getNickname())
                .build();
    }
}
