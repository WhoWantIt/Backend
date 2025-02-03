package gdg.whowantit.service;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.VolunteerConverter;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.User;
import gdg.whowantit.entity.Volunteer;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.repository.VolunteerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    public VolunteerResponseDto postVolunteer(VolunteerRequestDto volunteerRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Volunteer volunteer = VolunteerConverter.convertToVolunteer(volunteerRequestDto);
        volunteer.setBeneficiary(beneficiaryRepository.findByBeneficiaryId(user.getId()).
                orElseThrow(() -> new TempHandler(ErrorStatus.BENEFICIARY_NOT_FOUND)));
        volunteer.setApprovalStatus(ApprovalStatus.UNDETERMINED);
        volunteer.setCurrentCapacity(0L);


        volunteerRepository.save(volunteer);

        VolunteerResponseDto volunteerResponseDto = VolunteerConverter.convertToVolunteerResponseDto(volunteer);
        volunteerResponseDto.setNickname(user.getNickname());
        volunteerResponseDto.setVolunteerId(volunteer.getVolunteerId());
        volunteerResponseDto.setBeneficiaryId(user.getId());
        return volunteerResponseDto;
    }
}
