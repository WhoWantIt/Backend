package gdg.whowantit.service;
import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.UserConverter;
import gdg.whowantit.dto.TokenResponse;
import gdg.whowantit.dto.UserDto.SignInRequestDto;
import gdg.whowantit.dto.UserDto.SignUpRequestDto;
import gdg.whowantit.dto.UserDto.UserResponseDto;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Role;
import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.RefreshTokenRepository;
import gdg.whowantit.repository.SponsorRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.service.ImageService.ImageService;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final SponsorRepository sponsorRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final TokenService tokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ImageService imageService;
    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.existsByEmail(signUpRequestDto.getEmail())) {
            throw new TempHandler(ErrorStatus.EMAIL_ALREADY_EXIST);
        }

        User user = UserConverter.toUser(signUpRequestDto);


        userRepository.save(user);


        if (user.getRole() == Role.SPONSOR){
            Sponsor sponsor = new Sponsor();
            sponsor.setUser(user);
            sponsorRepository.save(sponsor);

        } else if (user.getRole() == Role.BENEFICIARY){
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setUser(user);
            beneficiaryRepository.save(beneficiary);
        }

        return UserConverter.toResponseDto(user);
    }

    public TokenResponse signIn(SignInRequestDto signInRequestDto){
        User user = userRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new TempHandler(ErrorStatus.LOGIN_ERROR_ID));

        if (!user.getPassword().equals(signInRequestDto.getPassword())) {
            throw new TempHandler(ErrorStatus.LOGIN_ERROR_PW);
        }

        String accessToken = tokenService.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = tokenService.generateRefreshToken(user.getEmail(), user.getRole());

        return new TokenResponse(accessToken, refreshToken);
    }

    public UserResponseDto getMyInfo(){
        // Refresh Token 삭제
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        return UserConverter.toResponseDto(user);
    }

    public void deleteUser(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if (user.getImage() != null) {
            String image = user.getImage();
            imageService.deleteImage("user", image);
        }
        userRepository.delete(user);
        refreshTokenRepository.deleteByEmail(email);
    }


}

