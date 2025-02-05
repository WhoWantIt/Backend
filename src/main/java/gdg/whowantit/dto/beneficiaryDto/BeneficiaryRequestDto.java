package gdg.whowantit.dto.beneficiaryDto;

import lombok.Getter;

public class BeneficiaryRequestDto {
    @Getter
    public static class profileRequest {
        private String info;

        private Long toddler;

        private Long child;

        private Long adolescent;

        private Long youth;
    }
}
