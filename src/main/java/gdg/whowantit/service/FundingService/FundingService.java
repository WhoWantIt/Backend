package gdg.whowantit.service.FundingService;

import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;

public interface FundingService {
    FundingResponseDto.createResponse createFunding(FundingRequestDto.createRequest request);
}
