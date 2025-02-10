package gdg.whowantit.dto.PostDto;

import lombok.Builder;
import lombok.Getter;

public class PostRequestDto {

    @Getter
    @Builder
    public static class BeneficiaryPostRequestDto {
        private String title;

        private String content;

    }

}
