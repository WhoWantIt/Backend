package gdg.whowantit.converter;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.Volunteer;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class VolunteerConverter {
    public static VolunteerResponseDto convertToVolunteerResponseDto(Volunteer volunteer) {
        VolunteerResponseDto volunteerResponseDto = new VolunteerResponseDto();
        BeanUtils.copyProperties(volunteer, volunteerResponseDto);
        return volunteerResponseDto;
    }

    public static Volunteer convertToVolunteer(VolunteerRequestDto volunteerRequestDto) {
        Volunteer volunteer = new Volunteer();
        BeanUtils.copyProperties(volunteerRequestDto, volunteer);
        volunteer.setStartTime(LocalDateTime.parse(volunteerRequestDto.getStartTime()));
        volunteer.setDeadline(LocalDateTime.parse(volunteerRequestDto.getDeadline()));
        return volunteer;
    }

    public static Page<VolunteerResponseDto> convertToVolunteerResponseDtoPage(Page<Volunteer> volunteerPage) {
        return (volunteerPage.map(volunteer -> {
            VolunteerResponseDto dto = new VolunteerResponseDto();
            BeanUtils.copyProperties(volunteer, dto);  // 엔티티 필드 자동 복사

            // 추가 정보 입력
            if (volunteer.getBeneficiary() != null) {
                dto.setBeneficiaryId(volunteer.getBeneficiary().getBeneficiaryId());
                dto.setNickname(volunteer.getBeneficiary().getUser().getNickname());
            }

            return dto;
        }));
    }

    public static BeneficiaryResponseDto.volunteerResponse toVolunteerResponse (Volunteer volunteer) {

        LocalDate deadline = volunteer.getDeadline().toLocalDate();
        LocalDate today = LocalDate.now();

        long dDay = ChronoUnit.DAYS.between(today, deadline);
        String dDayString = (dDay < 0) ? "마감" : String.valueOf(dDay);

        return BeneficiaryResponseDto.volunteerResponse.builder()
                .volunteerId(volunteer.getVolunteerId())
                .title(volunteer.getTitle())
                .address(volunteer.getBeneficiary().getUser().getAddress())
                .dDay(dDayString)
                .beneficiaryId(volunteer.getBeneficiary().getBeneficiaryId())
                .beneficiaryName(volunteer.getBeneficiary().getUser().getNickname())
                .build();
    }

}
