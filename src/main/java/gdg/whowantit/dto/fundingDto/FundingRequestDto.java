package gdg.whowantit.dto.fundingDto;

import gdg.whowantit.entity.Beneficiary;
import lombok.Getter;

import java.time.LocalDateTime;

public class FundingRequestDto {
    @Getter
    public static class createRequest {
        private String title;

        private String product_name;

        private float target_amount;

        private String text;

        private LocalDateTime deadline;

    }
}
