package gdg.whowantit.converter;

import gdg.whowantit.dto.MyDto.MyResponseDto;
import gdg.whowantit.entity.User;

public class MyConverter {

    public static MyResponseDto.MyResponse toMyResponse (User user){
        return MyResponseDto.MyResponse.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }
}
