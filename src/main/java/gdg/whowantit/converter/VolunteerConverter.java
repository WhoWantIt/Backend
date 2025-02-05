package gdg.whowantit.converter;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.Volunteer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VolunteerConverter {
    public static BeneficiaryResponseDto.volunteerResponse toVolunteerResponse (Volunteer volunteer) {

        LocalDate deadline = volunteer.getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        return BeneficiaryResponseDto.volunteerResponse.builder()
                .volunteerId(volunteer.getVolunteerId())
                .title(volunteer.getTitle())
                .address(volunteer.getBeneficiary().getUser().getAddress())
                .dDay(dDayString)
                .beneficiaryId(volunteer.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(volunteer.getBeneficiary().getUser().getNickname())
                .build();
    }
}
