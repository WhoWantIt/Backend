package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.service.BeneficiaryService.BeneficiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beneficiaries")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.my-beneficiary}")
public class BeneficiaryController {
    private final BeneficiaryService beneficiaryService;

    @GetMapping("/fundings/{beneficiaryId}")
    @Operation(summary="수혜자 - 게시한 펀딩 리스트 조회 API",
            description="수혜자 - 게시한 펀딩 리스트 조회 API")
    public ApiResponse<BeneficiaryResponseDto.fundingListResponse> getFundingList() {
        BeneficiaryResponseDto.fundingListResponse fundingResponses = beneficiaryService.getFundingList();

        return ApiResponse.onSuccess(fundingResponses);
    }

    @GetMapping("/volunteers/{beneficiaryId}")
    @Operation(summary="수혜자 - 게시한 자원봉사 공고 리스트 조회 API",
            description="수혜자 - 게시한 자원봉사 공고 리스트 조회 API")
    public ApiResponse<BeneficiaryResponseDto.volunteerListResponse> getVolunteerList() {
        BeneficiaryResponseDto.volunteerListResponse volunteerResponse = beneficiaryService.getVolunteerList();

        return ApiResponse.onSuccess(volunteerResponse);
    }

    @GetMapping("/posts/{beneficiaryId}")
    @Operation(summary="수혜자 - 게시글 리스트 조회 API",
            description="수혜자 - 게시글 리스트 조회 API")
    public ApiResponse<BeneficiaryResponseDto.postListResponse> getPostList(){
        BeneficiaryResponseDto.postListResponse postResponse = beneficiaryService.getPostList();

        return ApiResponse.onSuccess(postResponse);
    }
}

