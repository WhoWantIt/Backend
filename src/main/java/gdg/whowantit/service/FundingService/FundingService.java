package gdg.whowantit.service.FundingService;

import gdg.whowantit.dto.beneficiaryDto.BeneficiaryResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRelationResponseDto;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.Status;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface FundingService {
    FundingResponseDto.createResponse createFunding(FundingRequestDto.createRequest request);
    FundingResponseDto.createResponse createFundingImage(Long fundingId, List<MultipartFile> images);
    FundingResponseDto.createResponse updateFunding(Long fundingId, boolean permission);
    List<BeneficiaryResponseDto.fundingResponse> getFundingList();
    List<BeneficiaryResponseDto.fundingResponse> getFundingList(Status status);
    FundingResponseDto.infoResponse getFundingInfo(Long fundingId);
    List<FundingResponseDto.sponsorResponse> getSponsorList(Long fundingId);
    FundingResponseDto.scrapResponse scrapFunding(Long fundingId);
    void deleteScrapFunding(Long fundingId);
    FundingRelationResponseDto.createResponse createSpon(Long fundingId, float paymentAmount);

}
