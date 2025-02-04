package gdg.whowantit.service.BeneficiaryService;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryRequestDto;
import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;

public interface BeneficiaryService {
    BeneficiaryResponseDto.fundingListResponse getFundingList(Long beneficiaryId);
    BeneficiaryResponseDto.volunteerListResponse getVolunteerList(Long beneficiaryId);
    BeneficiaryResponseDto.postListResponse getPostList(Long beneficiaryId);
    BeneficiaryResponseDto.profileResponse getProfile(Long beneficiaryId);
    BeneficiaryResponseDto.profileResponse updateProfile(BeneficiaryRequestDto.profileRequest request);
}
