package gdg.whowantit.service.MyService;


import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.MyConverter;
import gdg.whowantit.dto.MyDto.MyRequestDto;
import gdg.whowantit.dto.MyDto.MyResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.UserRepository;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService{
    //private final JwtManager jwtManager;
    private final UserRepository userRepository;

    //userId를 token으로 바꿀 예정
    @Override
    @Transactional
    public MyResponseDto.MyResponse getProfile(Long userId){
        //Long userId=jwtManager.validateJwt(accessToken);
        User user=userRepository.findById(userId)
                .orElseThrow(()->new TempHandler(ErrorStatus.LOGIN_ERROR_ID));

        return MyConverter.toMyResponse(user);
    }

    @Override
    @Transactional
    public MyResponseDto.MyResponse updateProfile(Long userId, MyRequestDto.MyRequest request){
        //Long userId=jwtManager.validateJwt(accessToken);
        User user=userRepository.findById(userId)
                .orElseThrow(()->new TempHandler(ErrorStatus.LOGIN_ERROR_ID));

        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        return MyConverter.toMyResponse(userRepository.save(user));
    }

}
