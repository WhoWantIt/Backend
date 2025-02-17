package gdg.whowantit.dto.MyDto;

import lombok.Builder;
import lombok.Getter;

public class MyResponseDto {

    @Getter
    @Builder
    public static class MyResponse {
        private Long id;

        private String nickname;

        private String name;

        private String email;

        private String phoneNumber;

        private String address;

        private String image;
    }
}
