package gdg.whowantit.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        servers = {@Server(url = "/", description = "Default Server URL")},
        security = {@SecurityRequirement(name = "BearerAuth")}
)
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI WhoWantItAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("WhoWantIt API")
                        .description("""
                                ### WhoWantIt REST API 명세 문서입니다.
                                - #### 🔐 `Authorization` 헤더에 JWT 토큰을 입력할 수 있습니다.
                                - #### 🔑 로그인 후 발급받은 Access Token을 사용하세요.
                                """)
                        .version("v0.1"))
                .components(new Components().addSecuritySchemes("BearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP) // ✅ APIKEY → HTTP로 변경
                                .scheme("bearer") // ✅ `bearer` 스키마 설정
                                .bearerFormat("JWT") // ✅ JWT 형식 명시
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")));
    }
}
