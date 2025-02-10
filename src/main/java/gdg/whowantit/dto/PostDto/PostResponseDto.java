package gdg.whowantit.dto.PostDto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

public class PostResponseDto {

    @Getter
    @Builder
    public static class BeneficiaryPostResponseDto {
        private Long postId;

        private Long beneficiaryId;

        private String title;

        private String content;

        private List<String> attachedImages;

        private String attachedExcelFile;

    }

}