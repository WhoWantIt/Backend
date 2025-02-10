package gdg.whowantit.dto.volunteerDto;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Field;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VolunteerResponseDto {
    private Long volunteerId;
    private Long beneficiaryId;
    private String nickname; // entity에는 포함되어 있지 않지만 사용자에게 응답 줄 때는 주는게 좋을 것 같아서
    private String title;
    private Field field;
    private String content;
    private String attachedImage;
    private LocalDateTime startTime;
    private LocalDateTime deadline;
    private Long maxCapacity;
    private Long currentCapacity;
    private ApprovalStatus approvalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
