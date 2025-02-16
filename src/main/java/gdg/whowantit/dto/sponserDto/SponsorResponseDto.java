package gdg.whowantit.dto.sponserDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class SponsorResponseDto {

    @Getter
    @Builder
    public static class fundingResponse {

        private Long fundingId;

        private String title;

        private String attachedImage;

        private String dDay;

        private float fundingAmount;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;

    }

    @Getter
    @Builder
    public static class fundingListResponse {
        private String sponsorName;

        private String sponsorNickname;

        private double totalAmount;

        private List<fundingResponse> fundingList;
    }

    @Getter
    @Builder
    public static class scrapedFundingResponse {

        private Long fundingId;

        private Long fundingScrapId;

        private float currentAmount;

        private float targetAmount;

        private String title;

        private String attachedImage;

        private String dDay;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;

    }

    @Getter
    @Builder
    public static class volunteerResponse {

        private Long volunteerId;

        private String title;

        private String address;

        private String dDay;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;

    }

    @Getter
    @Builder
    public static class volunteerListResponse {
        private String sponsorName;

        private String sponsorNickname;

        private int volunteerListCount;

        private List<volunteerResponse> volunteerList;

    }

    @Getter
    @Builder
    public static class scrapedVolunteerResponse {
        private Long volunteerId;

        private Long scrapId;

        private String title;

        private LocalDateTime startTime;

        private String dDay;

        private String address;

        private Long currentCapacity;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;
    }

}
