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
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/volunteers")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.volunteer-sponsor}")


public class VolunteerController {
    private final VolunteerService volunteerService;

    @Operation(summary = "자원봉사 공고글 게시", description = "복지시설에서 자원봉사 공고글 게시 요청 기능입니다.")
    @PostMapping("")
    public ResponseEntity<ApiResponse<VolunteerResponseDto>> postVolunteer(@RequestBody VolunteerRequestDto volunteerRequestDto) {
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

    @Operation(summary = "자원봉사 전체 게시글 조회", description = "자원봉사 전체 공고글 조회하는 기능입니다.")
    @GetMapping("")
    public ResponseEntity<ApiResponse<Page<VolunteerResponseDto>>> getAllVolunteers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<VolunteerResponseDto> volunteers = volunteerService.getAllVolunteers(pageable);
        return ResponseEntity.ok(ApiResponse.onSuccess(volunteers));
    }

    @Operation(summary = "자원봉사 게시글 상세 조회", description = "후원자가 자원봉사 공고글 상세 조회하는 기능입니다.")
    @GetMapping("/{volunteerId}")
    public ResponseEntity<ApiResponse<VolunteerResponseDto>> getVolunteerDetail(@PathVariable Long volunteerId) {
        VolunteerResponseDto volunteerResponseDto = volunteerService.getVolunteerDetail(volunteerId);
        return ResponseEntity.ok(ApiResponse.onSuccess(volunteerResponseDto));
    }

    @Operation(summary = "지역별 봉사 리스트 조회",
            description = "후원자가 지역별로 봉사 리스트를 조회하는 기능입니다." +
                    "ex) 서울시 전체인 경우 city에만 서울시 넣고 district는 첨부 X" +
                    "서울시 도봉산구의 경우 city에 서울시 넣고 district에 도봉산구")
    @GetMapping("/regions")
    public ResponseEntity<ApiResponse<Page<VolunteerResponseDto>>> getVolunteersByCategory
            (@RequestParam @Nullable String city,
             @RequestParam @Nullable String district,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "10") int size) {


        Pageable pageable = PageRequest.of(page, size);
        Page<VolunteerResponseDto> volunteers = volunteerService.getAdressFilteredVolunteers(city, district, pageable);
        return ResponseEntity.ok(ApiResponse.onSuccess(volunteers));
    }



}
