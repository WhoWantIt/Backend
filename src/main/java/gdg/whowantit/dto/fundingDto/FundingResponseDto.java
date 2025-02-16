package gdg.whowantit.dto.fundingDto;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Status;
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
    @Getter
    @Builder
    public static class infoResponse {
        private Long fundingId;

        private String title;

        private String content;

        private Status status;

        private String productName;

        private Float currentAmount;

        private String attachedImage;

        private ApprovalStatus approvalStatus;

        private double attainmentPercent;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;
    }

    @Getter
    @Builder
    public static class sponsorResponse {
        private Long sponsorId;

        private String sponsorNickname;

        private Long fundingRelationId;

        private float paymentAmount;

    }

    @Getter
    @Builder
    public static class scrapResponse{
        private Long scrapId;

        private Long sponsorId;

        private Long fundingId;
    }

}
