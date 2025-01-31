package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.MyDto.MyRequestDto;
import gdg.whowantit.dto.MyDto.MyResponseDto;
import gdg.whowantit.service.MyService.MyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor

@Tag(name = "${swagger.tag.auth}")

public class MyController {
    private final MyService myService;

    @GetMapping("/info")
    @Operation(summary="후원자, 수혜자 - 사용자 개인정보 조회 API",
            description="후원자, 수혜자 - 사용자 개인정보 조회 API")
    public ApiResponse<MyResponseDto.MyResponse> getProfile (){

        MyResponseDto.MyResponse response=myService.getProfile();

        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/info")
    @Operation(summary="후원자, 수혜자 - 사용자 개인정보 수정 API",
            description="후원자, 수혜자 - 사용자 개인정보 수정 API")
    public ApiResponse<MyResponseDto.MyResponse> updateProfile(MyRequestDto.MyRequest request){
        MyResponseDto.MyResponse response=myService.updateProfile(request);

        return ApiResponse.onSuccess(response);
    }

}
