package gdg.whowantit.dto.ScrapDto;

import gdg.whowantit.entity.Volunteer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScrapResponseDto {
    private Long scrapId;

    private Long sponsorId;

    private Long volunteerId;
}
