package gdg.whowantit.dto.sponserDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class SponsorResponseDto {

    @Getter
    @Builder
    public static class fundingResponse {

        private Long fundingId;


    }

    @Getter
    @Builder
    public static class volunteerResponse {

        private Long volunteerId;

        private String title;

        private String address;

        private LocalDateTime deadline;

        private Long beneficiaryId;

        private String beneficiaryName;

    }

}
