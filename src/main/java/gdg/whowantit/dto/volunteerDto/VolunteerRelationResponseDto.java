package gdg.whowantit.dto.volunteerDto;

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
