package gdg.whowantit.converter;

import gdg.whowantit.dto.ScrapDto.ScrapResponseDto;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.FundingScrap;
import gdg.whowantit.entity.Scrap;
import org.springframework.beans.BeanUtils;

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
                .beneficiaryNickname(scrap.getVolunteer().getBeneficiary().getUser().getNickname())
                .build();
    }

    public static SponsorResponseDto.scrapedFundingResponse toScrapFundingResponse (FundingScrap fundingScrap) {

        LocalDate deadLine= fundingScrap.getFunding().getDeadline().toLocalDate();
        LocalDate today= LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadLine);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        return SponsorResponseDto.scrapedFundingResponse.builder()
                .fundingId(fundingScrap.getFunding().getFundingId())
                .fundingScrapId(fundingScrap.getFundingScrapId())
                .currentAmount(fundingScrap.getFunding().getCurrentAmount())
                .targetAmount(fundingScrap.getFunding().getTargetAmount())
                .title(fundingScrap.getFunding().getTitle())
                .attachedImage(fundingScrap.getFunding().getAttachedImage())
                .dDay(dDayString)
                .beneficiaryId(fundingScrap.getFunding().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(fundingScrap.getFunding().getBeneficiary().getUser().getName())
                .beneficiaryNickname(fundingScrap.getFunding().getBeneficiary().getUser().getNickname())
                .build();
    }

    public static ScrapResponseDto toScrapResponseDto (Scrap scrap) {
        ScrapResponseDto scrapResponseDto = new ScrapResponseDto();
        scrapResponseDto.setScrapId(scrap.getScrapId());
        scrapResponseDto.setSponsorId(scrap.getSponsor().getSponsorId());
        scrapResponseDto.setVolunteerId(scrap.getVolunteer().getVolunteerId());
        return scrapResponseDto;
    }
}
