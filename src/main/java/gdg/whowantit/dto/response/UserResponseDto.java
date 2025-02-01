package gdg.whowantit.dto.response;

import gdg.whowantit.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserResponseDto {
    private Long id;
    private String name;
    private Role role;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
}
