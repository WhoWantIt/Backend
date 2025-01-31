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
<<<<<<< HEAD
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
=======
>>>>>>> 1289d00 (feat: 사용자 개인정보 API 구현)
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MyServiceImpl implements MyService{
    //private final JwtManager jwtManager;
    private final UserRepository userRepository;

<<<<<<< HEAD

    @Override
    @Transactional
    public MyResponseDto.MyResponse getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

=======
    //userId를 token으로 바꿀 예정
    @Override
    @Transactional
    public MyResponseDto.MyResponse getProfile(Long userId){
        //Long userId=jwtManager.validateJwt(accessToken);
        User user=userRepository.findById(userId)
                .orElseThrow(()->new TempHandler(ErrorStatus.LOGIN_ERROR_ID));
>>>>>>> 1289d00 (feat: 사용자 개인정보 API 구현)

        return MyConverter.toMyResponse(user);
    }

    @Override
    @Transactional
<<<<<<< HEAD
    public MyResponseDto.MyResponse updateProfile(MyRequestDto.MyRequest request){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
=======
    public MyResponseDto.MyResponse updateProfile(Long userId, MyRequestDto.MyRequest request){
        //Long userId=jwtManager.validateJwt(accessToken);
        User user=userRepository.findById(userId)
                .orElseThrow(()->new TempHandler(ErrorStatus.LOGIN_ERROR_ID));
>>>>>>> 1289d00 (feat: 사용자 개인정보 API 구현)

        user.setNickname(request.getNickname());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setAddress(request.getAddress());

        return MyConverter.toMyResponse(userRepository.save(user));
    }

}
