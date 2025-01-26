package gdg.whowantit.controller;
import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.TokenResponse;
import gdg.whowantit.dto.request.SignInRequestDto;
import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor

public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<UserResponseDto>> signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        UserResponseDto userResponseDto = userService.signUp(signUpRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(userResponseDto));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ApiResponse<TokenResponse>> signIn(@RequestBody SignInRequestDto signInRequestDto) {
        TokenResponse tokens = userService.signIn(signInRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(tokens));
    }




}
