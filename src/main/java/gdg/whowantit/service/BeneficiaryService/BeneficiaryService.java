package gdg.whowantit.service.BeneficiaryService;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryRequestDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;

public interface BeneficiaryService {
    BeneficiaryResponseDto.fundingListResponse getFundingList();
    BeneficiaryResponseDto.volunteerListResponse getVolunteerList();
    BeneficiaryResponseDto.postListResponse getPostList();
    BeneficiaryResponseDto.profileResponse getProfile();
    BeneficiaryResponseDto.profileResponse updateProfile(BeneficiaryRequestDto.profileRequest request);
}
