package gdg.whowantit.dto.beneficiaryDto;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class BeneficiaryResponseDto {
    @Getter
    @Builder
    public static class fundingResponse {

        private Long fundingId;

        private String title;

        private String attachedImage;

        private String dDay;

        private Long attainmentPercent;

        private Long beneficiaryId;

        private String beneficiaryName;

    }

    @Getter
    @Builder
    public static class fundingListResponse {
        private String beneficiaryName;

        private int listCount;

        private List<SponsorResponseDto.fundingResponse> fundingList;
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

    }

    @Getter
    @Builder
    public static class volunteerListResponse {

        private int volunteerListCount;

        private List<SponsorResponseDto.volunteerResponse> volunteerList;

    }

    @Getter
    @Builder
    public static class postResponse{
        private Long postId;

        private String title;

        private Long beneficiaryId;

        private String beneficiaryName;

        private boolean isVerified;
    }

}
