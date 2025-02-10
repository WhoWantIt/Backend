package gdg.whowantit.converter;

import gdg.whowantit.dto.response.VolunteerAppliedSponsorsDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.Volunteer;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

public class SponsorConverter {

    public static Page<VolunteerAppliedSponsorsDto> convertToSponsorResponseDtoPage(Page<Sponsor> sponsorPage) {
        return (sponsorPage.map(sponsor -> {
            VolunteerAppliedSponsorsDto dto = new VolunteerAppliedSponsorsDto();
            BeanUtils.copyProperties(sponsor.getUser(), dto);  // 엔티티 필드 자동 복사

            // 추가 정보 입력
            if (sponsor.getUser().getImage() != null) {
                dto.setImage(sponsor.getUser().getImage());
            }
            dto.setId(sponsor.getUser().getId());

            return dto;
        }));
    }
}
