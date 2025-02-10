package gdg.whowantit.config;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Component // ✅ @Configuration 대신 사용 가능
public class CorsConfig implements WebMvcConfigurer { // ✅ 인터페이스 직접 구현
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // ✅ 모든 엔드포인트 CORS 허용
                .allowedOrigins("http://localhost:3000", "http://localhost:5173", "https://example.com") // ✅ 허용할 도메인 설정
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // ✅ 허용할 HTTP 메서드
                .allowedHeaders("*") // ✅ 모든 요청 헤더 허용
                .exposedHeaders("Authorization") // ✅ JWT 토큰 포함 헤더 노출
                .allowCredentials(true); // ✅ 쿠키 포함 허용
    }
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
