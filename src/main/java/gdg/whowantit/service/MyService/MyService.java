package gdg.whowantit.service.MyService;

import gdg.whowantit.dto.MyDto.MyRequestDto;
import gdg.whowantit.dto.MyDto.MyResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MyService {

    MyResponseDto.MyResponse getProfile();
    MyResponseDto.MyResponse updateProfile(MyRequestDto.MyRequest request);
    MyResponseDto.MyResponse updateProfileImage(MultipartFile image);

}
