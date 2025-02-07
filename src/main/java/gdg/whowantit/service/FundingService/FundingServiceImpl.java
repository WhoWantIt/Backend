package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.fundingDto.FundingRequestDto;
import gdg.whowantit.dto.fundingDto.FundingResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FundingServiceImpl implements FundingService{
    private final UserRepository userRepository;
    @Override
    @Transactional
    public FundingResponseDto.createResponse createFunding(FundingRequestDto.createRequest request){

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));

    }

}
