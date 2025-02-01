package gdg.whowantit.service;
import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.UserConverter;
import gdg.whowantit.dto.TokenResponse;
import gdg.whowantit.dto.request.SignInRequestDto;
import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Role;
import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.RefreshTokenRepository;
import gdg.whowantit.repository.SponsorRepository;
import gdg.whowantit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public UserResponseDto signUp(SignUpRequestDto signUpRequestDto){
        User savedUser = userRepository.save(UserConverter.toUser(signUpRequestDto));

        if (savedUser.getRole() == Role.SPONSOR){
            Sponsor sponsor = new Sponsor();
            sponsor.setUser(savedUser);
            sponsorRepository.save(sponsor);

        } else if (savedUser.getRole() == Role.BENEFICIARY){
            Beneficiary beneficiary = new Beneficiary();
            beneficiary.setUser(savedUser);
            beneficiaryRepository.save(beneficiary);
        }


        return UserConverter.toResponseDto(savedUser);
    }

    public TokenResponse signIn(SignInRequestDto signInRequestDto){
        User user = userRepository.findByEmail(signInRequestDto.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!user.getPassword().equals(signInRequestDto.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String accessToken = tokenService.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = tokenService.generateRefreshToken(user.getEmail(), user.getRole());

        return new TokenResponse(accessToken, refreshToken);
    }

    public UserResponseDto getMyInfo(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // Refresh Token 삭제
        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        return UserConverter.toResponseDto(user);
    }

    public void deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        userRepository.delete(user);
        refreshTokenRepository.deleteByEmail(email);
    }


}

