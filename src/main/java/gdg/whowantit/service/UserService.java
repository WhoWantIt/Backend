package gdg.whowantit.service;
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
import gdg.whowantit.repository.SponsorRepository;
import gdg.whowantit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SponsorRepository sponsorRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final TokenService tokenService;

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
        String refreshToken = tokenService.generateRefreshToken(user.getEmail());

        return new TokenResponse(accessToken, refreshToken);
    }
}
