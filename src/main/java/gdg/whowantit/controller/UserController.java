package gdg.whowantit.controller;
import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.TokenResponse;
import gdg.whowantit.dto.request.SignInRequestDto;
import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.service.TokenService;
import gdg.whowantit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;
    private final TokenService tokenService;

    @Operation(summary = "회원가입", description = "유저 회원가입 입니다.")
    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserResponseDto>> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto userResponseDto = userService.signUp(signUpRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(userResponseDto));
    }

    @Operation(summary = "로그인", description = "유저 로그인 입니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<TokenResponse>> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        TokenResponse tokens = userService.signIn(signInRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(tokens));
    }

    @Operation(summary = "로그아웃", description = "유저 로그아웃 입니다.")
    @PostMapping("/sign-out")
    public ResponseEntity<ApiResponse<Void>> signOut() {
        tokenService.logout();
        return ResponseEntity.noContent().build();
    }






}
