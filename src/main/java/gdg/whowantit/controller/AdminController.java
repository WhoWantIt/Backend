package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.adminDto.AdminResponseDto;
import gdg.whowantit.service.AdminService.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.my-author}")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/beneficiaries")
    @Operation(summary="관리자 - 수혜자 목록 조회 API",
            description="관리자 - 수혜자 목록 조회 API")
    public ApiResponse<AdminResponseDto.beneficiaryListResponse> getBeneficiaryList(){
        AdminResponseDto.beneficiaryListResponse response = adminService.getBeneficiaryList();

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/sponsors")
    @Operation(summary="관리자 - 후원자 목록 조회 API",
            description="관리자 - 후원자 목록 조회 API")
    public ApiResponse<AdminResponseDto.sponsorListResponse> getSponsorList(){
        AdminResponseDto.sponsorListResponse response = adminService.getSponsorList();

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/posts")
    @Operation(summary="관리자 - 게시글 인증 요청 목록 조회 API",
            description="관리자 - 게시글 인증 요청 목록 조회 API")
    public ApiResponse<AdminResponseDto.postListResponse> getPostList(){
        AdminResponseDto.postListResponse response = adminService.getPostList();

        return ApiResponse.onSuccess(response);
    }

    @GetMapping("/fundings")
    @Operation(summary="관리자 - 펀딩 인증 요청 목록 조회 API",
            description="관리자 - 펀딩 인증 요청 목록 조회 API")
    public ApiResponse<AdminResponseDto.fundingListResponse> getFundingList(){
        AdminResponseDto.fundingListResponse response = adminService.getFundingList();

        return ApiResponse.onSuccess(response);
    }
}
