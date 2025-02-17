package gdg.whowantit.converter;

import gdg.whowantit.dto.adminDto.AdminResponseDto;
import gdg.whowantit.dto.volunteerDto.VolunteerAppliedSponsorsDto;
import gdg.whowantit.entity.Sponsor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.util.List;

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

    public static AdminResponseDto.sponsorResponse toSponsorResponse (Sponsor sponsor){
        return AdminResponseDto.sponsorResponse.builder()
                .attachedImage(sponsor.getUser().getImage())
                .name(sponsor.getUser().getName())
                .nickname(sponsor.getUser().getNickname())
                .build();
    }
}
