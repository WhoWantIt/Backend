package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.kakaoPayDto.KakaoPayResponseDto;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.UserRepository;
import gdg.whowantit.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class KakaopayServiceImpl implements KakaopayService{
    private final KakaoPayProperties payProperties;
    private final RestTemplate restTemplate =new RestTemplate();
    private KakaoPayResponseDto.KakaoReadyResponse kakaoReady;
    private final UserRepository userRepository;

    public HttpHeaders getHeaders(){
        HttpHeaders headers=new HttpHeaders();
        String auth="SECRET_KEY "+payProperties.getSecretKey();
        headers.set("Authorization",auth);
        headers.set("Content-Type","application/json");
        return headers;

    }
    public KakaoPayResponseDto.KakaoReadyResponse kakaoPayReady(float paymentAmount) {
        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("partner_order_id", "ORDER_ID");
        parameters.put("partner_user_id","USER_ID");
        parameters.put("item_name", "ITEM_NAME");
        parameters.put("quantity", "1");
        parameters.put("total_amount", paymentAmount);
        parameters.put("vat_amount", "100");
        parameters.put("tax_free_amount", "0");
        parameters.put("approval_url", "http://13.209.33.88:8080/success"); //url 주소 수정 필요
        parameters.put("cancel_url", "http://13.209.33.88:8080/cancel"); //url 주소 수정 필요
        parameters.put("fail_url", "http://13.209.33.88:8080/fail");

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        RestTemplate restTemplate = new RestTemplate();
        kakaoReady = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/ready",
                requestEntity,
                KakaoPayResponseDto.KakaoReadyResponse.class);
        return kakaoReady;
    }
    public KakaoPayResponseDto.KakaoApproveResponse approveResponse (String pgToken) {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        //카카오 요청
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("tid", kakaoReady.getTid());
        parameters.put("partner_order_id", "ORDER_ID");
        parameters.put("partner_user_id", "USER_ID");
        parameters.put("pg_token", pgToken);

        //파라미터, 헤더
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        System.out.println();
        System.out.println();
        System.out.println(requestEntity);
        System.out.println();
        System.out.println();

        //외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayResponseDto.KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://open-api.kakaopay.com/online/v1/payment/approve",
                requestEntity,
                KakaoPayResponseDto.KakaoApproveResponse.class);
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(approveResponse);
        System.out.println();
        System.out.println();
        System.out.println();
        return approveResponse;
    }





}
