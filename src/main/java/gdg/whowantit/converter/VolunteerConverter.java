package gdg.whowantit.converter;

import gdg.whowantit.dto.request.SignUpRequestDto;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.UserResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.entity.Volunteer;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

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
}
