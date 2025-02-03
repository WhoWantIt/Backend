package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.converter.VolunteerConverter;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.Volunteer;
import gdg.whowantit.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/volunteers")
@RequiredArgsConstructor
public class VolunteerController {
    private final VolunteerService volunteerService;

    @Operation(summary = "자원봉사 공고글 게시", description = "복지시설에서 자원봉사 공고글 게시 요청 기능입니다.")
    @PostMapping("")
    public ResponseEntity<ApiResponse<VolunteerResponseDto>> postVolunteer(VolunteerRequestDto volunteerRequestDto) {
        VolunteerResponseDto volunteerResponseDto =
                volunteerService.postVolunteer(volunteerRequestDto);
        return ResponseEntity.ok(ApiResponse.onSuccess(volunteerResponseDto));
    }


}
