package gdg.whowantit.converter;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.Scrap;

import java.time.LocalDateTime;

public class ScrapConverter {
    public static SponsorResponseDto.scrapedVolunteerResponse toScrapedVolunteerResponse (Scrap scrap){
        return SponsorResponseDto.scrapedVolunteerResponse.builder()
                .volunteerId(scrap.getVolunteer().getVolunteerId())
                .scrapId(scrap.getScrapId())
                .title(scrap.getVolunteer().getTitle())
                .startTime(scrap.getVolunteer().getStartTime())
                .deadline(scrap.getVolunteer().getDeadline())
                .address(scrap.getVolunteer().getBeneficiary().getUser().getAddress())
                .currentCapacity(scrap.getVolunteer().getCurrentCapacity())
                .beneficiaryId(scrap.getVolunteer().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(scrap.getVolunteer().getBeneficiary().getUser().getName())
                .build();
    }
}
