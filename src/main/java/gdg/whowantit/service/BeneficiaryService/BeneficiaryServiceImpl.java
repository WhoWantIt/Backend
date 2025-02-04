package gdg.whowantit.service.BeneficiaryService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.*;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryRequestDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
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
public class BeneficiaryServiceImpl implements BeneficiaryService{
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final VolunteerRepository volunteerRepository;
    private final PostRepository postRepository;
    private final BeneficiaryRepository beneficiaryRepository;

    @Override
    @Transactional
    public BeneficiaryResponseDto.fundingListResponse getFundingList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Funding> fundings=fundingRepository.findByBeneficiary_beneficiaryId(user.getId());

        int listCount= fundings.size();

        List<BeneficiaryResponseDto.fundingResponse> fundingResponses= fundings.stream()
                .map(FundingConverter::toFundingResponse)
                .collect(Collectors.toList());

        return BeneficiaryResponseDto.fundingListResponse.builder()
                .beneficiaryName(user.getName())
                .listCount(listCount)
                .fundingList(fundingResponses)
                .build();
    }

    @Override
    @Transactional
    public BeneficiaryResponseDto.volunteerListResponse getVolunteerList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Volunteer> volunteers=volunteerRepository.findByBeneficiary_beneficiaryId(user.getId());

        int volunteerListCount =volunteers.size();

        List<BeneficiaryResponseDto.volunteerResponse> volunteerResponses= volunteers.stream()
                .map(VolunteerConverter::toVolunteerResponse)
                .collect(Collectors.toList());

        return BeneficiaryResponseDto.volunteerListResponse.builder()
                .volunteerListCount(volunteerListCount)
                .volunteerList(volunteerResponses)
                .build();
    }

    @Override
    @Transactional
    public BeneficiaryResponseDto.postListResponse getPostList(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        List<Post> posts=postRepository.findByBeneficiary_beneficiaryId(user.getId());

        int postListCount = posts.size();

        List<BeneficiaryResponseDto.postResponse> postResponses= posts.stream()
                .map(PostConverter::toPostResponse)
                .collect(Collectors.toList());

        return BeneficiaryResponseDto.postListResponse.builder()
                .postListCount(postListCount)
                .postList(postResponses)
                .build();
    }

    @Override
    @Transactional
    public BeneficiaryResponseDto.profileResponse getProfile(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        Beneficiary beneficiary=beneficiaryRepository.findById(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        return BeneficiaryConverter.toBeneficiaryResponse(beneficiary);
    }

    @Override
    @Transactional
    public BeneficiaryResponseDto.profileResponse updateProfile(BeneficiaryRequestDto.profileRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Beneficiary beneficiary=beneficiaryRepository.findById(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

        beneficiary.setInfo(request.getInfo());
        beneficiary.setToddler(request.getToddler());
        beneficiary.setChild(request.getChild());
        beneficiary.setAdolescent(request.getAdolescent());
        beneficiary.setYouth(request.getYouth());

        return BeneficiaryConverter.toBeneficiaryResponse(beneficiaryRepository.save(beneficiary));

    }
}

