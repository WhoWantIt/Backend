package gdg.whowantit.controller;
import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.TokenResponse;
import gdg.whowantit.dto.request.SignInRequestDto;
import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.service.TokenService;
import gdg.whowantit.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
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

    @Operation(summary = "개인 정보 조회", description = "유저 개인 정보 조회 입니다.")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponseDto>> me() {
        UserResponseDto userResponseDto = userService.getMyInfo();
        return ResponseEntity.ok(ApiResponse.onSuccess(userResponseDto));
    }


    @Operation(summary = "access 토큰 갱신", description = "refreshToken을 이용한 accessToken 갱신입니다.")
    @GetMapping("/tokens/update")
    public ResponseEntity<ApiResponse<TokenResponse>> refreshToken(HttpServletRequest request) {
        String refreshToken = (String) request.getAttribute("refreshToken");
        if (refreshToken == null) {
            throw new TempHandler(ErrorStatus.TOKEN_NOT_FOUND);
        }

        System.out.println("UserController.refreshToken");
        TokenResponse tokenResponse = tokenService.regenerateAccessToken(request);
        System.out.println("UserController.refreshToken");
        System.out.println("refreshToken: " + tokenResponse.getAccessToken() + "accessToken: " + tokenResponse.getRefreshToken());
        return ResponseEntity.ok(ApiResponse.onSuccess(tokenResponse));
    }





}
