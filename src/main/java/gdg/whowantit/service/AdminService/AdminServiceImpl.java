package gdg.whowantit.service.AdminService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.converter.BeneficiaryConverter;
import gdg.whowantit.converter.FundingConverter;
import gdg.whowantit.converter.PostConverter;
import gdg.whowantit.converter.SponsorConverter;
import gdg.whowantit.dto.adminDto.AdminResponseDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.entity.*;
import gdg.whowantit.repository.*;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{
    private final UserRepository userRepository;
    private final FundingRepository fundingRepository;
    private final PostRepository postRepository;
    private final BeneficiaryRepository beneficiaryRepository;
    private final SponsorRepository sponsorRepository;

    @Override
    @Transactional
    public AdminResponseDto.beneficiaryListResponse getBeneficiaryList(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.ADMIN){
            throw new TempHandler(ErrorStatus.PERMISSION_DENIED);
        }

        List<Beneficiary> beneficiaries=beneficiaryRepository.findAll();
        List<AdminResponseDto.beneficiaryResponse> beneficiaryResponses=beneficiaries.stream()
                .map(BeneficiaryConverter::toBeneficiaryListResponse)
                .collect(Collectors.toList());

        return AdminResponseDto.beneficiaryListResponse.builder()
                .beneficiaryListCount(beneficiaries.size())
                .beneficiaryList(beneficiaryResponses)
                .build();
    }
    @Override
    @Transactional
    public AdminResponseDto.sponsorListResponse getSponsorList(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.ADMIN){
            throw new TempHandler(ErrorStatus.PERMISSION_DENIED);
        }

        List<Sponsor> sponsors=sponsorRepository.findAll();
        List<AdminResponseDto.sponsorResponse> sponsorsResponse=sponsors.stream()
                .map(SponsorConverter::toSponsorResponse)
                .collect(Collectors.toList());

        return AdminResponseDto.sponsorListResponse.builder()
                .sponsorListCount(sponsors.size())
                .sponsorList(sponsorsResponse)
                .build();
    }
    @Override
    @Transactional
    public AdminResponseDto.postListResponse getPostList(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.ADMIN){
            throw new TempHandler(ErrorStatus.PERMISSION_DENIED);
        }

        List<Post> posts=postRepository.findAllByApprovalStatus(ApprovalStatus.UNDETERMINED);
        int listCount=posts.size();

        List<BeneficiaryResponseDto.postResponse> postResponses= posts.stream()
                .map(PostConverter::toPostResponse)
                .collect(Collectors.toList());

        return AdminResponseDto.postListResponse.builder()
                .postListCount(listCount)
                .postList(postResponses)
                .build();
    }

    @Override
    @Transactional
    public AdminResponseDto.fundingListResponse getFundingList(){
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        if(user.getRole() != Role.ADMIN){
            throw new TempHandler(ErrorStatus.PERMISSION_DENIED);
        }

        List<Funding> fundings=fundingRepository.findAllByApprovalStatus(ApprovalStatus.UNDETERMINED);

        int listCount= fundings.size();

        List<BeneficiaryResponseDto.fundingResponse> fundingResponses= fundings.stream()
                .map(FundingConverter::toFundingResponse)
                .collect(Collectors.toList());

        return AdminResponseDto.fundingListResponse.builder()
                .fundingListCount(listCount)
                .fundingResponseList(fundingResponses)
                .build();
    }
}
