package gdg.whowantit.dto.beneficiaryDto;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.ApprovalStatus;
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

        private double attainmentPercent;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;

    }

    @Getter
    @Builder
    public static class fundingListResponse {
        private String beneficiaryName;

        private String beneficiaryNickname;

        private int listCount;

        private List<BeneficiaryResponseDto.fundingResponse> fundingList;
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

        private int volunteerListCount;

        private List<BeneficiaryResponseDto.volunteerResponse> volunteerList;

    }

    @Getter
    @Builder
    public static class postResponse{
        private Long postId;

        private String title;

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;

        private boolean isVerified;

        private ApprovalStatus approvalStatus;


    }

    @Getter
    @Builder
    public static class postListResponse {

        private int postListCount;

        private List<BeneficiaryResponseDto.postResponse> postList;

    }

    @Getter
    @Builder
    public static class profileResponse {

        private Long beneficiaryId;

        private String beneficiaryName;

        private String beneficiaryNickname;

        private String image;

        private String email;

        private String phoneNumber;

        private String address;

        private String info;

        private Long toddler;

        private Long child;

        private Long adolescent;

        private Long youth;
    }

}
