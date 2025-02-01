package gdg.whowantit.security;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
@Component  // ✅ 이제 Spring 빈으로 등록
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = jwtUtil.extractToken(request);  // 요청에서 JWT 추출
        String requestURI = request.getRequestURI();
        String tokenType = jwtUtil.getTokenType(token);

        // ✅ Swagger 관련 요청은 필터를 그냥 통과시키기
        if (requestURI.startsWith("/users/sign-in") || requestURI.startsWith("/users/sign-up") ||
                requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }
        System.out.println(tokenType);
        if ("refresh".equals(tokenType)) {
            request.setAttribute("refreshToken", token);
            filterChain.doFilter(request, response);
        }



        if (tokenType.equals("access")){
            try {
                if (jwtUtil.validateToken(token, "access")) {  // ✅ 토큰이 유효한 경우 SecurityContext 설정
                    String email = jwtUtil.getEmailFromToken(token);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                // ✅ 만료된 토큰은 예외 발생시키지 않고 계속 진행 → Refresh Token 검증을 위해 요청 유지
                request.setAttribute("expiredToken", token);  // ✅ 만료된 토큰을 리퀘스트에 저장
            } catch (JwtException e) {
                // ✅ 잘못된 토큰은 즉시 차단 (위조된 경우)
                throw new TempHandler(ErrorStatus.TOKEN_UNVALID);
            }

            filterChain.doFilter(request, response);
        }
    }

}

