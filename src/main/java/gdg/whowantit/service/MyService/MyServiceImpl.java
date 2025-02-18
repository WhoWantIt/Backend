package gdg.whowantit.service.MyService;


import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.aws.s3.AmazonS3Manager;
import gdg.whowantit.converter.MyConverter;
import gdg.whowantit.dto.MyDto.MyRequestDto;
import gdg.whowantit.dto.MyDto.MyResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.util.SecurityUtil;
import gdg.whowantit.util.StringListUtil;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService{

    private final UserRepository userRepository;
    private final AmazonS3Manager s3Manager;


    @Override
    @Transactional
    public MyResponseDto.MyResponse getProfile(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));



        return MyConverter.toMyResponse(user);
    }

    @Override
    @Transactional
    public MyResponseDto.MyResponse updateProfile(MyRequestDto.MyRequest request){

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        return MyConverter.toMyResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public MyResponseDto.MyResponse updateProfileImage(MultipartFile image){

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        String imageUrl = s3Manager.uploadFile("myInfo", image);
        user.setImage(imageUrl);

        return MyConverter.toMyResponse(userRepository.save(user));
    }

}
