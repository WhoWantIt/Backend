package gdg.whowantit.service.AdminService;

import gdg.whowantit.dto.adminDto.AdminResponseDto;

public interface AdminService {
    AdminResponseDto.beneficiaryListResponse getBeneficiaryList();
    AdminResponseDto.sponsorListResponse getSponsorList();
    AdminResponseDto.postListResponse getPostList();
    AdminResponseDto.fundingListResponse getFundingList();
}
