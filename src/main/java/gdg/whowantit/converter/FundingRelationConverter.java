package gdg.whowantit.converter;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.FundingRelation;

public class FundingRelationConverter {
    public static SponsorResponseDto.fundingResponse toFundingResponse(FundingRelation fundingRelation){
        return SponsorResponseDto.fundingResponse.builder()
                .fundingId(fundingRelation.getFunding().getFundingId())
                .title(fundingRelation.getFunding().getTitle())
                .attachedImage(fundingRelation.getFunding().getAttachedImage())
                .fundingAmount(fundingRelation.getPaymentAmount())
                .beneficiaryId(fundingRelation.getFunding().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(fundingRelation.getFunding().getBeneficiary().getUser().getNickname())
                .build();
    }
}