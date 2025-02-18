package gdg.whowantit.dto.PostDto;

import gdg.whowantit.entity.ApprovalStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PostResponseDto {

    @Getter
    @Builder
    public static class BeneficiaryPostResponseDto {
        private Long postId;

        private Long beneficiaryId;

        private String nickname;

        private String title;

        private String content;

        private List<String> attachedImages;

        private String attachedExcelFile;

        private ApprovalStatus approvalStatus;

        private Boolean isVerified;

        private String createdAt;
    }

}