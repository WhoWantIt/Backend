package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.converter.VolunteerConverter;
import gdg.whowantit.converter.VolunteerRelationConverter;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.VolunteerRelationResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.Volunteer;
import gdg.whowantit.entity.VolunteerRelation;
import gdg.whowantit.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "자원봉사 지원", description = "후원자가 자원봉사 공고글에 신청하는 기능입니다.")
    @PostMapping("/{volunteerId}")
    public ResponseEntity<ApiResponse<VolunteerRelationResponseDto>> applyVolunteer(@PathVariable Long volunteerId) {

        VolunteerRelationResponseDto volunteerRelationResponseDto = volunteerService.applyVolunteer(volunteerId);
        return ResponseEntity.ok(ApiResponse.onSuccess(volunteerRelationResponseDto));
    }

    @Operation(summary = "자원봉사 지원 취소", description = "자원봉사 공고글에 신청했던 후원자가 다시 신청 취소하는 기능입니다.")
    @DeleteMapping("/{volunteerId}")
    public ResponseEntity<ApiResponse<Void>> cancelVolunteerApplication(@PathVariable Long volunteerId) {
        volunteerService.cancelVolunteerApplication(volunteerId);
        return ResponseEntity.noContent().build();
    }

}
