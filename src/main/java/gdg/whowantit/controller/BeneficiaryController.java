package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryRequestDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.service.BeneficiaryService.BeneficiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/beneficiaries")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.my-beneficiary}")
public class BeneficiaryController {
    private final BeneficiaryService beneficiaryService;

    @GetMapping("/fundings/{beneficiaryId}")
    @Operation(summary="수혜자 - 게시한 펀딩 리스트 조회 API",
            description="수혜자 - 게시한 펀딩 리스트 조회 API")
    public ApiResponse<BeneficiaryResponseDto.fundingListResponse> getFundingList(@PathVariable("beneficiaryId") Long beneficiaryId) {
        BeneficiaryResponseDto.fundingListResponse fundingResponses = beneficiaryService.getFundingList(beneficiaryId);

        return ApiResponse.onSuccess(fundingResponses);
    }

    @GetMapping("/volunteers/{beneficiaryId}")
    @Operation(summary="수혜자 - 게시한 자원봉사 공고 리스트 조회 API",
            description="수혜자 - 게시한 자원봉사 공고 리스트 조회 API")
    public ApiResponse<BeneficiaryResponseDto.volunteerListResponse> getVolunteerList(@PathVariable("beneficiaryId") Long beneficiaryId) {
        BeneficiaryResponseDto.volunteerListResponse volunteerResponse = beneficiaryService.getVolunteerList(beneficiaryId);

        return ApiResponse.onSuccess(volunteerResponse);
    }

    @GetMapping("/posts/{beneficiaryId}")
    @Operation(summary="수혜자 - 게시글 리스트 조회 API",
            description="수혜자 - 게시글 리스트 조회 API")
    public ApiResponse<BeneficiaryResponseDto.postListResponse> getPostList(@PathVariable("beneficiaryId") Long beneficiaryId){
        BeneficiaryResponseDto.postListResponse postResponse = beneficiaryService.getPostList(beneficiaryId);

        return ApiResponse.onSuccess(postResponse);
    }

    @GetMapping("/profiles/{beneficiaryId}")
    @Operation(summary="수혜자 - 프로필 조회 API",
            description="수혜자 - 프로필 조회 API")
    public ApiResponse<BeneficiaryResponseDto.profileResponse> getProfile(@PathVariable("beneficiaryId") Long beneficiaryId){
        BeneficiaryResponseDto.profileResponse profileResponse = beneficiaryService.getProfile(beneficiaryId);

        return ApiResponse.onSuccess(profileResponse);
    }

    @PutMapping("/profiles/details")
    @Operation(summary="수혜자 - 프로필 상세 정보 추가 API",
            description="수혜자 - 프로필 상세 정보 추가 API")
    public ApiResponse<BeneficiaryResponseDto.profileResponse> updateProfile(@RequestBody BeneficiaryRequestDto.profileRequest request){
        BeneficiaryResponseDto.profileResponse profileResponse = beneficiaryService.updateProfile(request);

        return ApiResponse.onSuccess(profileResponse);
    }
}

