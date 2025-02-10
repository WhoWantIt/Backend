package gdg.whowantit.converter;

import gdg.whowantit.dto.response.VolunteerRelationResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.VolunteerRelation;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VolunteerRelationConverter {

    public static VolunteerRelationResponseDto convertVolunteerRelationToVolunteerResponseDto
            (VolunteerRelation volunteerRelation) {
        VolunteerRelationResponseDto volunteerRelationResponseDto = new VolunteerRelationResponseDto();

        volunteerRelationResponseDto.setVolunteerRelationId
                (volunteerRelation.getVolunteerRelationId());
        volunteerRelationResponseDto.setVolunteerId
                (volunteerRelation.getVolunteer().getVolunteerId());
        volunteerRelationResponseDto.setBeneficiaryId
                (volunteerRelation.getBeneficiary().getBeneficiaryId());
        volunteerRelationResponseDto.setSponsorId
                (volunteerRelation.getSponsor().getSponsorId());

        return volunteerRelationResponseDto;
    }


    public static SponsorResponseDto.volunteerResponse toVolunteerResponse (VolunteerRelation volunteerRelation) {

        LocalDate deadline = volunteerRelation.getVolunteer().getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        return SponsorResponseDto.volunteerResponse.builder()
                .volunteerId(volunteerRelation.getVolunteer().getVolunteerId())
                .title(volunteerRelation.getVolunteer().getTitle())
                .address(volunteerRelation.getVolunteer().getBeneficiary().getUser().getAddress())
                .dDay(dDayString)
                .beneficiaryId(volunteerRelation.getVolunteer().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(volunteerRelation.getVolunteer().getBeneficiary().getUser().getNickname())
                .build();
    }


}
