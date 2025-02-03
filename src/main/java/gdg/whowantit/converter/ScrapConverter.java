package gdg.whowantit.converter;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.Scrap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ScrapConverter {
    public static SponsorResponseDto.scrapedVolunteerResponse toScrapedVolunteerResponse (Scrap scrap){

        LocalDate deadline = scrap.getVolunteer().getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        return SponsorResponseDto.scrapedVolunteerResponse.builder()
                .volunteerId(scrap.getVolunteer().getVolunteerId())
                .scrapId(scrap.getScrapId())
                .title(scrap.getVolunteer().getTitle())
                .startTime(scrap.getVolunteer().getStartTime())
                .dDay(dDayString)
                .address(scrap.getVolunteer().getBeneficiary().getUser().getAddress())
                .currentCapacity(scrap.getVolunteer().getCurrentCapacity())
                .beneficiaryId(scrap.getVolunteer().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(scrap.getVolunteer().getBeneficiary().getUser().getName())
                .build();
    }
}
