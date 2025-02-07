package gdg.whowantit.dto.response;

import gdg.whowantit.entity.Beneficiary;
import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.Volunteer;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRelationResponseDto {
    private Long volunteerRelationId;

    private Long volunteerId;

    private Long beneficiaryId;

    private Long sponsorId;
}
