package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.Status;
import gdg.whowantit.service.FundingService.FundingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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


    @Tag(name = "${swagger.tag.cloudfunding-sponsor}")
    @Tag(name = "${swagger.tag.cloudfunding-beneficiary}")
    @GetMapping("/lists")
    @Operation(summary="클라우드 펀딩 조회  ( ALL )",
            description="클라우드 펀딩 조회  ( ALL )")
    public ApiResponse<List<BeneficiaryResponseDto.fundingResponse>> getFundingList(){
        List<BeneficiaryResponseDto.fundingResponse> response = fundingService.getFundingList();

        return ApiResponse.onSuccess(response);
    }

    @Tag(name = "${swagger.tag.cloudfunding-sponsor}")
    @Tag(name = "${swagger.tag.cloudfunding-beneficiary}")
    @GetMapping("/filters")
    @Operation(summary="클라우드 펀딩 조회 ( 진행중, 진행완료 )",
            description="클라우드 펀딩 조회 ( 진행중, 진행완료 )")
    public ApiResponse<List<BeneficiaryResponseDto.fundingResponse>> getFundingList(@RequestParam Status status){
        List<BeneficiaryResponseDto.fundingResponse> response = fundingService.getFundingList(status);

        return ApiResponse.onSuccess(response);
    }

    @Tag(name = "${swagger.tag.cloudfunding-sponsor}")
    @Tag(name = "${swagger.tag.cloudfunding-beneficiary}")
    @GetMapping("/{fundingId}")
    @Operation(summary="클라우드 펀딩 상세 조회",
            description="클라우드 펀딩 상세 조회")
    public ApiResponse<FundingResponseDto.infoResponse> getFundingInfo(@PathVariable @Valid Long fundingId){
        FundingResponseDto.infoResponse response = fundingService.getFundingInfo(fundingId);

        return ApiResponse.onSuccess(response);
    }
    @Tag(name = "${swagger.tag.cloudfunding-beneficiary}")
    @GetMapping("/sponsors/{fundingId}")
    @Operation(summary="펀딩 신청한 후원자 리스트 조회",
            description="펀딩 신청한 후원자 리스트 조회")
    public ApiResponse<List<FundingResponseDto.sponsorResponse>>getSponsorList(@PathVariable @Valid Long fundingId){
        List<FundingResponseDto.sponsorResponse> response = fundingService.getSponsorList(fundingId);

        return ApiResponse.onSuccess(response);
    }

    @Tag(name = "${swagger.tag.cloudfunding-sponsor}")
    @PostMapping("/scraps/{fundingId}")
    @Operation(summary="클라우드 펀딩 스크랩 등록",
            description="클라우드 펀딩 스크랩 등록")
    public ApiResponse<FundingResponseDto.scrapResponse>scrapFunding(@PathVariable @Valid Long fundingId){
        FundingResponseDto.scrapResponse response = fundingService.scrapFunding(fundingId);

        return ApiResponse.onSuccess(response);
    }

    @Tag(name = "${swagger.tag.cloudfunding-sponsor}")
    @DeleteMapping("/scraps/{fundingId}")
    @Operation(summary="클라우드 펀딩 스크랩 취소",
            description="클라우드 펀딩 스크랩 취소")
    public ApiResponse<String>deleteScrapFunding(@PathVariable @Valid Long fundingId){
        fundingService.deleteScrapFunding(fundingId);

        return ApiResponse.onSuccess("스크랩 취소 성공");
    }



}
