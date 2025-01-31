package gdg.whowantit.dto.MyDto;

import lombok.Builder;
import lombok.Getter;

public class MyRequestDto {
    @Getter
    @Builder
    public static class MyRequest {
        private String nickname;

        private String email;

        private String password;

        private String phoneNumber;

        private String address;
    }
}
