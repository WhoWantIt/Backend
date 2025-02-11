package gdg.whowantit.dto.fundingDto;

import gdg.whowantit.entity.Beneficiary;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class FundingResponseDto {
    @Getter
    @Builder
    public static class createResponse {
        private Long fundingId;

        private String title;

        private String product_name;

        private float target_amount;

        private String text;

        private String status;

        private String approval_status;

        private LocalDateTime deadline;

        private long beneficiaryId;
    }

}
