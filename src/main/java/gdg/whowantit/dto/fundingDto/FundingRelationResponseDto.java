package gdg.whowantit.dto.fundingDto;

import lombok.Builder;
import lombok.Getter;

public class FundingRelationResponseDto {
    @Getter
    @Builder
    public static class createResponse{

        private Long sponsorId;

        private Long fundingId;

        private float paymentAmount;
    }
}
