package gdg.whowantit.service.FundingService;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.kakaoPayDto.KakaoPayResponseDto;
import gdg.whowantit.entity.FundingRelation;
import gdg.whowantit.entity.PaymentStatus;
import gdg.whowantit.entity.Sponsor;
import gdg.whowantit.entity.User;
import gdg.whowantit.repository.FundingRelationRepository;
import gdg.whowantit.repository.SponsorRepository;
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
    private final UserRepository userRepository;
    private final FundingRelationRepository fundingRelationRepository;
    private final SponsorRepository sponsorRepository;

    public HttpHeaders getHeaders(){
        HttpHeaders headers=new HttpHeaders();
        String auth="SECRET_KEY "+payProperties.getSecretKey();
        headers.set("Authorization",auth);
        headers.set("Content-Type","application/json");
        return headers;

    }

    public KakaoPayResponseDto.KakaoApproveResponse approveResponse (String pgToken) {

        String email = SecurityUtil.getCurrentUserEmail();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        Sponsor sponsor = sponsorRepository.findById(user.getId())
                .orElseThrow(() -> new TempHandler(ErrorStatus.USER_NOT_FOUND));
        FundingRelation fundingRelation = fundingRelationRepository
                .findTopBySponsorOrderByFundingRelationIdDesc(sponsor)
                .orElseThrow(() -> new TempHandler(ErrorStatus.FUNDING_NOT_FOUND));
        String tid = fundingRelation.getTid();
        //카카오 요청
        Map<String, String> parameters = new HashMap<>();
        parameters.put("cid", payProperties.getCid());
        parameters.put("tid", tid);
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
        fundingRelation.setPaymentStatus(PaymentStatus.SUCCESS);
        fundingRelationRepository.save(fundingRelation);
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
