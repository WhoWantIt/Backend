package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.Status;
import gdg.whowantit.service.FundingService.FundingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fundings")
@RequiredArgsConstructor
//@Tag(name = "${swagger.tag.cloudfunding-sponsor}")
//@Tag(name = "${swagger.tag.cloudfunding-author}")
public class FundingController {
    private final FundingService fundingService;
    @PostMapping("/")
    @Operation(summary="수혜자 - 펀딩 생성 API",
            description="수혜자 - 펀딩 생성 API")
    @Tag(name = "${swagger.tag.cloudfunding-beneficiary}")
    public ApiResponse<FundingResponseDto.createResponse> createFunding(@RequestBody FundingRequestDto.createRequest request) {
        FundingResponseDto.createResponse response = fundingService.createFunding(request);

        return ApiResponse.onSuccess(response);
    }

    @PutMapping("/approvals/{fundingId}")
    @Operation(summary = "관리자 - 펀딩 게시글 승인, 거절 API",
            description = "관리자 - 펀딩 게시글 승인, 거절 API")
    @Tag(name = "${swagger.tag.cloudfunding-author}")
    public ApiResponse<FundingResponseDto.createResponse> updateFunding(@PathVariable @Valid Long fundingId, @RequestParam boolean permission ){
        FundingResponseDto.createResponse response = fundingService.updateFunding(fundingId, permission);

        return ApiResponse.onSuccess(response);
    }

}
