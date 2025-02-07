package gdg.whowantit.service;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.ScrapConverter;
import gdg.whowantit.converter.SponsorConverter;
import gdg.whowantit.converter.VolunteerConverter;
import gdg.whowantit.converter.VolunteerRelationConverter;
import gdg.whowantit.dto.ScrapDto.ScrapResponseDto;
import gdg.whowantit.dto.request.VolunteerRequestDto;
import gdg.whowantit.dto.response.VolunteerAppliedSponsorsDto;
import gdg.whowantit.dto.response.VolunteerRelationResponseDto;
import gdg.whowantit.dto.response.VolunteerResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.*;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private final UserRepository userRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final VolunteerRelationRepository volunteerRelationRepository;
    private final SponsorRepository sponsorRepository;
    private final ScrapRepository scrapRepository;

    public VolunteerResponseDto postVolunteer(VolunteerRequestDto volunteerRequestDto) {

        String email = SecurityUtil.getCurrentUserEmail(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Volunteer volunteer = VolunteerConverter.convertToVolunteer(volunteerRequestDto);
        volunteer.setBeneficiary(beneficiaryRepository.findByBeneficiaryId(user.getId()).
                orElseThrow(() -> new TempHandler(ErrorStatus.BENEFICIARY_NOT_FOUND)));
        volunteer.setApprovalStatus(ApprovalStatus.UNDETERMINED);
        volunteer.setStatus(Status.BEFORE_PROGRESS);
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

    public void cancelVolunteerApplication(Long volunteerId) {
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email).
                orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerId(volunteerId).
                orElseThrow(()->new TempHandler(ErrorStatus.VOLUNTEER_NOT_FOUND));

        VolunteerRelation volunteerRelation = volunteerRelationRepository.findByVolunteerAndSponsor(volunteer, user.getSponsor()).
                orElseThrow(() -> new TempHandler(ErrorStatus.VOLUNTEER_APPLICATION_NOT_FOUND));


        volunteerRelationRepository.
                deleteVolunteerRelationByVolunteerRelationId(volunteerRelation.getVolunteerRelationId());

    }

    public Page<VolunteerResponseDto> getAllVolunteers(Pageable pageable) {
        Page<Volunteer> volunteerPage = volunteerRepository.
                findByApprovalStatus(ApprovalStatus.APPROVED, pageable);

        return VolunteerConverter.convertToVolunteerResponseDtoPage(volunteerPage);

    }

    public VolunteerResponseDto getVolunteerDetail(Long volunteerId) {
        Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerId(volunteerId)
                .orElseThrow(()->new TempHandler(ErrorStatus.VOLUNTEER_NOT_FOUND));

        VolunteerResponseDto volunteerResponseDto =
                VolunteerConverter.convertToVolunteerResponseDto(volunteer);

        volunteerResponseDto.setBeneficiaryId(volunteer.getBeneficiary().getBeneficiaryId());
        volunteerResponseDto.setNickname(volunteer.getBeneficiary().getUser().getNickname());

        return volunteerResponseDto;
    }

    public Page<VolunteerResponseDto> getAddressFilteredVolunteers
            (String keyword1, String keyword2, Pageable pageable) {

        Page<Volunteer> volunteerPage = volunteerRepository.
                findByAddressContainingBoth(keyword1, keyword2, pageable);

        return VolunteerConverter.convertToVolunteerResponseDtoPage(volunteerPage);
    }

    public Page<VolunteerResponseDto> getVolunteerByField(Field field, Pageable pageable) {
        Page<Volunteer> volunteerPage = volunteerRepository.findVolunteerByField(field, pageable);

        return VolunteerConverter.convertToVolunteerResponseDtoPage(volunteerPage);
    }

    public Page<VolunteerAppliedSponsorsDto> getSponsorsByVolunteerId(Long volunteerId, Pageable pageable) {
        Page<Sponsor> sponsors = sponsorRepository.findByVolunteerId(volunteerId, pageable);
        return SponsorConverter.convertToSponsorResponseDtoPage(sponsors);
    }

    public ScrapResponseDto scrapVolunteer(Long volunteerId) {
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerId(volunteerId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.VOLUNTEER_NOT_FOUND));

        if (scrapRepository.existsBySponsor_SponsorIdAndVolunteer_VolunteerId(user.getId(), volunteerId)) {
            throw new TempHandler(ErrorStatus.VOLUNTEER_ALREADY_SCRAPPED);
        }

        Scrap scrap = new Scrap();
        scrap.setSponsor(user.getSponsor());
        scrap.setVolunteer(volunteer);
        scrapRepository.save(scrap);

        return ScrapConverter.toScrapResponseDto(scrap);

    }

    public void cancelScrapVolunteer(Long volunteerId) {
        String email = SecurityUtil.getCurrentUserEmail();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Volunteer volunteer = volunteerRepository.findVolunteerByVolunteerId(volunteerId)
                .orElseThrow(() -> new TempHandler(ErrorStatus.VOLUNTEER_NOT_FOUND));

        if (scrapRepository.existsBySponsor_SponsorIdAndVolunteer_VolunteerId(user.getId(), volunteerId)) {
            scrapRepository.deleteBySponsor_SponsorIdAndVolunteer_VolunteerId(user.getId(), volunteerId);
        } else {
            throw new TempHandler(ErrorStatus.VOLUNTEER_APPLICATION_NOT_FOUND);
        }

    }

}
