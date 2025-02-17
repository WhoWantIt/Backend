package gdg.whowantit.dto.adminDto;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.entity.ApprovalStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class AdminResponseDto {
    @Getter
    @Builder
    public static class beneficiaryResponse{
        private String attachedImage;

        private String nickname;

        private String name;

    }

    @Getter
    @Builder
    public static class beneficiaryListResponse{
        private int beneficiaryListCount;

        private List<AdminResponseDto.beneficiaryResponse> beneficiaryList;
    }

    @Getter
    @Builder
    public static class sponsorResponse{

        private String attachedImage;

        private String nickname;

        private String name;
    }

    @Getter
    @Builder
    public static class sponsorListResponse{

        private int sponsorListCount;

        private List<AdminResponseDto.sponsorResponse> sponsorList;
    }

    @Getter
    @Builder
    public static class postListResponse{
        private int postListCount;

        private List<BeneficiaryResponseDto.postResponse> postList;
    }

    @Getter
    @Builder
    public static class fundingListResponse{
        private int fundingListCount;

        private List<BeneficiaryResponseDto.fundingResponse> fundingResponseList;
    }
}
