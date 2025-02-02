package gdg.whowantit.dto.request;

import gdg.whowantit.entity.Field;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VolunteerRequestDto {
    private String nickname;
    private String title;
    private Field field;
    private String content;
    private String attachedImage;
    private String startTime;
    private String deadLine;
    private Long maxCapacity;
}
