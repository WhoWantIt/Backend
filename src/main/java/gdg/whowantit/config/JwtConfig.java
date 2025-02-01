package gdg.whowantit.config;

import gdg.whowantit.util.JwtUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class JwtConfig {

    @Value("${jwt.secret.key}")  // 🔥 application.yml에서 jwt.secret 값을 가져오기
    private String secretKey;

    @PostConstruct
    public void init() {
        System.out.println("@Value로 주입된 JWT Secret Key: " + secretKey);

        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("JWT Secret Key가 설정되지 않았습니다.");
        }

        JwtUtil.init(secretKey);  // ✅ JWT 유틸리티 클래스 초기화
    }
}
