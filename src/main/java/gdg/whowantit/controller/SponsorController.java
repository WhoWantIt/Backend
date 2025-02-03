package gdg.whowantit.controller;

import gdg.whowantit.apiPayload.ApiResponse;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.service.SponsorService.SponsorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/sponsors")
@RequiredArgsConstructor
@Tag(name = "${swagger.tag.my-sponsor}")
public class SponsorController {
    private final SponsorService sponsorService;

    @GetMapping("/volunteers")
    @Operation(summary="후원자 - 봉사 내역 리스트 조회 API",
            description="후원자 - 봉사 내역 리스트 조회 API")
    public ApiResponse<SponsorResponseDto.volunteerListResponse> getVolunteerList() {
        SponsorResponseDto.volunteerListResponse volunteerResponses =sponsorService.getVolunteerList();

        return ApiResponse.onSuccess(volunteerResponses);
    }

    @GetMapping({"/fundings"})
    @Operation(summary="후원자 - 후원 내역 리스트 조회 API",
            description="후원자 - 후원 내역 리스트 조회 API")
    public ApiResponse<SponsorResponseDto.fundingListResponse> getFundingList() {
        SponsorResponseDto.fundingListResponse fundingResponses = sponsorService.getFundingList();

        return ApiResponse.onSuccess(fundingResponses);
    }

    @GetMapping("/scraps/volunteers")
    @Operation(summary="후원자 - 스크랩한 봉사 리스트 조회 API",
            description="후원자 - 스크랩한 봉사 리스트 조회 API")
    public ApiResponse<List<SponsorResponseDto.scrapedVolunteerResponse>> getScrapedVolunteers(){
        List<SponsorResponseDto.scrapedVolunteerResponse> scrapedVolunteerResponses = sponsorService.getScrapedVolunteers();

        return ApiResponse.onSuccess(scrapedVolunteerResponses);
    }

    @GetMapping("/scraps/fundings)")
    @Operation(summary="후원자 - 스크랩한 후원 리스트 조회 API",
            description="후원자 - 스크랩한 후원 리스트 조회 API")
    public ApiResponse<List<SponsorResponseDto.scrapedFundingResponse>> getScrapedFundings(){
        List<SponsorResponseDto.scrapedFundingResponse> scrapedFundingResponses = sponsorService.getScrapedFundings();

        return ApiResponse.onSuccess(scrapedFundingResponses);
    }



}
