package gdg.whowantit.service;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.VolunteerConverter;
import gdg.whowantit.converter.VolunteerRelationConverter;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.VolunteerRelationResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.ApprovalStatus;
import gdg.whowantit.entity.User;
import gdg.whowantit.entity.Volunteer;
import gdg.whowantit.entity.VolunteerRelation;
import gdg.whowantit.repository.BeneficiaryRepository;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.repository.VolunteerRelationRepository;
import gdg.whowantit.repository.VolunteerRepository;
import gdg.whowantit.util.SecurityUtil;
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
    private final VolunteerRelationRepository volunteerRelationRepository;
    public VolunteerResponseDto postVolunteer(VolunteerRequestDto volunteerRequestDto) {

        String email = SecurityUtil.getCurrentUserEmail(); // 현재 로그인된 사용자의 이메일
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

    public VolunteerRelationResponseDto applyVolunteer(Long volunteerId) {
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));


        Volunteer volunteer = volunteerRepository.
                findVolunteerByVolunteerId(volunteerId).
                orElseThrow(() -> new TempHandler(ErrorStatus.VOLUNTEER_NOT_FOUND));

        if (volunteerRelationRepository.existsVolunteerRelationBySponsor(user.getSponsor()))
        {
            throw new TempHandler(ErrorStatus.VOLUNTEER_ALREADY_APPLIED);
        }

        // VolunteerRelation 객체 생성해서 정보 입력
        VolunteerRelation volunteerRelation = new VolunteerRelation();
        volunteerRelation.setVolunteer(volunteer);
        volunteerRelation.setBeneficiary(volunteer.getBeneficiary());
        volunteerRelation.setSponsor(user.getSponsor());
        volunteerRelationRepository.save(volunteerRelation);
        return VolunteerRelationConverter.
                convertVolunteerRelationToVolunteerResponseDto(volunteerRelation);
    }
}
