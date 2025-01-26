package gdg.whowantit.converter;
import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.entity.User;
import org.springframework.beans.BeanUtils;

public class UserConverter {


    // entity -> Dto
    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPassword(user.getPassword());
        return userResponseDto;
    }

    // Dto -> entity
    public static User toUser(SignUpRequestDto signUpRequestDto) {
        User user = new User();
        BeanUtils.copyProperties(signUpRequestDto, user); // 필드 매핑 자동 처리
        return user;
    }

}
