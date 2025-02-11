package gdg.whowantit.service.FundingService;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.Status;

import java.util.List;


public interface FundingService {
    FundingResponseDto.createResponse createFunding(FundingRequestDto.createRequest request);
    FundingResponseDto.createResponse updateFunding(Long fundingId, boolean permission);
    List<BeneficiaryResponseDto.fundingResponse> getFundingList();
    List<BeneficiaryResponseDto.fundingResponse> getFundingList(Status status);

}
