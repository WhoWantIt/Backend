package gdg.whowantit.service.MyService;

import gdg.whowantit.dto.MyDto.MyRequestDto;
import gdg.whowantit.dto.MyDto.MyResponseDto;

public interface MyService {
    MyResponseDto.MyResponse getProfile(Long userId);
    MyResponseDto.MyResponse updateProfile(Long userId, MyRequestDto.MyRequest request);
}
