package gdg.whowantit.dto.request;
import gdg.whowantit.entity.Role;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class SignUpRequestDto {
    private String name;
    private Role role;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private String address;
    private String info; // 후원자의 경우 null
}
