package gdg.whowantit.service.BeneficiaryService;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;

public interface BeneficiaryService {
    BeneficiaryResponseDto.fundingListResponse getFundingList();
    BeneficiaryResponseDto.volunteerListResponse getVolunteerList();
    BeneficiaryResponseDto.postListResponse getPostList();
}
