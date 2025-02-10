package gdg.whowantit.converter;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.Funding;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FundingConverter {
    public static BeneficiaryResponseDto.fundingResponse toFundingResponse(Funding funding){
        LocalDate deadline = funding.getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        float targetAmount=funding.getTargetAmount();

        float currentAmount=funding.getCurrentAmount();
        double attainmentPercent=currentAmount/targetAmount*100.0;

        return BeneficiaryResponseDto.fundingResponse.builder()
                .fundingId(funding.getFundingId())
                .title(funding.getTitle())
                .attachedImage(funding.getAttachedImage())
                .dDay(dDayString)
                .attainmentPercent(attainmentPercent)
                .beneficiaryId(funding.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(funding.getBeneficiary().getUser().getNickname())
                .build();
    }
}