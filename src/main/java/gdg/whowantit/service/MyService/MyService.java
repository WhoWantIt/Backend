package gdg.whowantit.service.MyService;

import gdg.whowantit.dto.MyDto.MyRequestDto;
import gdg.whowantit.dto.MyDto.MyResponseDto;

public interface MyService {
<<<<<<< HEAD
    MyResponseDto.MyResponse getProfile();
    MyResponseDto.MyResponse updateProfile(MyRequestDto.MyRequest request);
=======
    MyResponseDto.MyResponse getProfile(Long userId);
    MyResponseDto.MyResponse updateProfile(Long userId, MyRequestDto.MyRequest request);
>>>>>>> 1289d00 (feat: 사용자 개인정보 API 구현)
}
