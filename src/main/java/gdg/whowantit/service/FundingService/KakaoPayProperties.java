package gdg.whowantit.service.FundingService;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="kakaopay")
@Getter
@Setter
public class KakaoPayProperties {
    @Value("${spring.kakao.pay.secret-key}")
    private String secretKey;

    @Value("${spring.kakao.pay.cid}")
    private String cid;
}
