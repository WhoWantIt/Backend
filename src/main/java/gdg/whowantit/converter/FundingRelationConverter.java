package gdg.whowantit.converter;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.FundingRelation;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class FundingRelationConverter {
    public static SponsorResponseDto.fundingResponse toFundingResponse(FundingRelation fundingRelation){

        LocalDate deadline = fundingRelation.getFunding().getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        return SponsorResponseDto.fundingResponse.builder()
                .fundingId(fundingRelation.getFunding().getFundingId())
                .title(fundingRelation.getFunding().getTitle())
                .attachedImage(fundingRelation.getFunding().getAttachedImage())
                .dDay(dDayString)
                .fundingAmount(fundingRelation.getPaymentAmount())
                .beneficiaryId(fundingRelation.getFunding().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(fundingRelation.getFunding().getBeneficiary().getUser().getName())
                .beneficiaryNickname(fundingRelation.getFunding().getBeneficiary().getUser().getNickname())
                .build();
    }
}