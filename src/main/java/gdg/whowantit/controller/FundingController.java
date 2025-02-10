package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.service.FundingService.FundingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
