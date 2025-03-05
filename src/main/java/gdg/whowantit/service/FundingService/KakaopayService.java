package gdg.whowantit.service.FundingService;

import gdg.whowantit.dto.kakaoPayDto.KakaoPayResponseDto;
import org.springframework.http.HttpHeaders;

public interface KakaopayService {
    HttpHeaders getHeaders();
    KakaoPayResponseDto.KakaoReadyResponse kakaoPayReady();
    KakaoPayResponseDto.KakaoApproveResponse approveResponse (String pgToken);
}
