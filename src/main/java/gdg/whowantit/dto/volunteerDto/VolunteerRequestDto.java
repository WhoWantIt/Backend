package gdg.whowantit.dto.volunteerDto;

import gdg.whowantit.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerRequestDto {
    private String nickname;
    private String title;
    private Field field;
    private String content;
    private String startTime;
    private String deadline;
    private Long maxCapacity;
}
