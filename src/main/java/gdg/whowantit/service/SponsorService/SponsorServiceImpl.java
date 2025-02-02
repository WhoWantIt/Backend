package gdg.whowantit.service.SponsorService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.VolunteerRelationConverter;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.entity.VolunteerRelation;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.repository.VolunteerRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SponsorServiceImpl implements SponsorService{

    private final UserRepository userRepository;

    private final VolunteerRelationRepository volunteerRelationRepository;

    @Override
    @Transactional
    public List<SponsorResponseDto.volunteerResponse> getVolunteerList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<VolunteerRelation> volunteerRelations=volunteerRelationRepository.findByUserId(user.getId());

        return volunteerRelations.stream()
                .map(VolunteerRelationConverter::toVolunteerResponse)  // 여기서 변환
                .collect(Collectors.toList());
    }

}
