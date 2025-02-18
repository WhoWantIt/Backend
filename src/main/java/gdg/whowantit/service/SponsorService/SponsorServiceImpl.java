package gdg.whowantit.service.SponsorService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.FundingRelationConverter;
import gdg.whowantit.converter.ScrapConverter;
import gdg.whowantit.converter.VolunteerRelationConverter;
import gdg.whowantit.dto.sponserDto.SponsorResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.*;
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
    private final FundingRelationRepository fundingRelationRepository;
    private final ScrapRepository scrapRepository;
    private final FundingScrapRepository fundingScrapRepository;

    @Override
    @Transactional
    public SponsorResponseDto.volunteerListResponse getVolunteerList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<VolunteerRelation> volunteerRelations=volunteerRelationRepository.findBySponsor_SponsorId(user.getId());

        int volunteerListCount =volunteerRelations.size();

        List<SponsorResponseDto.volunteerResponse> volunteerResponses= volunteerRelations.stream()
                .map(VolunteerRelationConverter::toVolunteerResponse)
                .collect(Collectors.toList());

        return SponsorResponseDto.volunteerListResponse.builder()
                .sponsorName(user.getName())
                .volunteerListCount(volunteerListCount)
                .volunteerList(volunteerResponses)
                .build();
    }

    @Override
    @Transactional
    public SponsorResponseDto.fundingListResponse getFundingList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<FundingRelation> fundingRelations=fundingRelationRepository.findBySponsor_SponsorId(user.getId());

        double totalAmount = fundingRelations.stream()
                .mapToDouble(FundingRelation::getPaymentAmount)
                .sum();

        List<SponsorResponseDto.fundingResponse> fundingResponses= fundingRelations.stream()
                .map(FundingRelationConverter::toFundingResponse)
                .collect(Collectors.toList());

        return SponsorResponseDto.fundingListResponse.builder()
                .sponsorName(user.getName())
                .totalAmount(totalAmount)
                .fundingList(fundingResponses)
                .build();

    }

    @Override
    @Transactional
    public List<SponsorResponseDto.scrapedVolunteerResponse> getScrapedVolunteers(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
               .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Scrap> scrapList = scrapRepository.findBySponsor_SponsorId(user.getId());

        return scrapList.stream()
                .map(ScrapConverter::toScrapedVolunteerResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<SponsorResponseDto.scrapedFundingResponse> getScrapedFundings(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<FundingScrap> scrapList = fundingScrapRepository.findBySponsor_SponsorId(user.getId());

        return scrapList.stream()
                .map(ScrapConverter::toScrapFundingResponse)
                .collect(Collectors.toList());
    }

}
