package gdg.whowantit.service.SponsorService;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;

import java.util.List;

public interface SponsorService {
    SponsorResponseDto.volunteerListResponse getVolunteerList();
    SponsorResponseDto.fundingListResponse getFundingList();
    List<SponsorResponseDto.scrapedVolunteerResponse> getScrapedVolunteers();
    List<SponsorResponseDto.scrapedFundingResponse> getScrapedFundings();
}
