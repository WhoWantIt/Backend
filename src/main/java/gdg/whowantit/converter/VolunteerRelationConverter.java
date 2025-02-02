package gdg.whowantit.converter;

import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.VolunteerRelation;

public class VolunteerRelationConverter {
    //엔티티 자체를 DTO에서 참조하는 방식을 사용하려다가 불필요한 데이터 들어가서
    // 개별 참조가 필요한 필드만 선택해서 가져오기 때문에 성능 최적화를 위해 개별 참조로 했음.
    public static SponsorResponseDto.volunteerResponse toVolunteerResponse (VolunteerRelation volunteerRelation) {
        return SponsorResponseDto.volunteerResponse.builder()
                .volunteerId(volunteerRelation.getVolunteer().getVolunteerId())
                .title(volunteerRelation.getVolunteer().getTitle())
                .address(volunteerRelation.getVolunteer().getBeneficiary().getUser().getAddress())
                .deadline(volunteerRelation.getVolunteer().getDeadline())
                .beneficiaryId(volunteerRelation.getVolunteer().getBeneficiary().getBeneficiaryId())
                .beneficiaryName(volunteerRelation.getVolunteer().getBeneficiary().getUser().getNickname())
                .build();
    }
}
