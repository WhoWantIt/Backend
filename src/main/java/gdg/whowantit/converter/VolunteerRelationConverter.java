package gdg.whowantit.converter;

import gdg.whowantit.dto.response.VolunteerRelationResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.VolunteerRelation;
import org.springframework.beans.BeanUtils;

public class VolunteerRelationConverter {

    public static VolunteerRelationResponseDto convertVolunteerRelationToVolunteerResponseDto
            (VolunteerRelation volunteerRelation) {
        VolunteerRelationResponseDto volunteerRelationResponseDto = new VolunteerRelationResponseDto();

        volunteerRelationResponseDto.setVolunteerId
                (volunteerRelation.getVolunteer().getVolunteerId());
        volunteerRelationResponseDto.setBeneficiaryId
                (volunteerRelation.getBeneficiary().getBeneficiaryId());
        volunteerRelationResponseDto.setSponsorId
                (volunteerRelation.getSponsor().getSponsorId());

        return volunteerRelationResponseDto;
    }

}
