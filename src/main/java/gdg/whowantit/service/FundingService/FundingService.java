package gdg.whowantit.service.FundingService;

import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.Status;

public interface FundingService {
    FundingResponseDto.createResponse createFunding(FundingRequestDto.createRequest request);
    FundingResponseDto.createResponse updateFunding(Long fundingId, boolean permission);
}
