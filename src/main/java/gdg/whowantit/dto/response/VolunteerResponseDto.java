package gdg.whowantit.dto.response;

import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.Field;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class VolunteerResponseDto {
    private String volunteerId;
    private String beneficiaryId;
    private String nickname; // entity에는 포함되어 있지 않지만 사용자에게 응답 줄 때는 주는게 좋을 것 같아서
    private String title;
    private Field field;
    private String content;
    private String attachedImage;
    private String startTime;
    private String deadLine;
    private Long maxCapacity;
    private Long currentCapacity;
    private ApprovalStatus approvalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
