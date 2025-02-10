package gdg.whowantit.dto.UserDto;
import gdg.whowantit.entity.Role;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String name;
    private Role role;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
}
