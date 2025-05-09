package gdg.whowantit.security;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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

        String token = jwtUtil.extractToken(request);
        String requestURI = request.getRequestURI();

        // Swagger & 공개 API 통과
        if (requestURI.startsWith("/users/sign-in") || requestURI.startsWith("/users/sign-up")
                || requestURI.startsWith("/swagger-ui") || requestURI.startsWith("/v3/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            if (token != null && !token.isEmpty()) {
                String tokenType = jwtUtil.getTokenType(token);

                if ("access".equals(tokenType)) {
                    if (jwtUtil.validateToken(token, "access")) {
                        String email = jwtUtil.getEmailFromToken(token);
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } else if ("refresh".equals(tokenType)) {
                    request.setAttribute("refreshToken", token);
                }
            }
        } catch (ExpiredJwtException e) {
            request.setAttribute("expiredToken", token);
        } catch (JwtException e) {
            throw new TempHandler(ErrorStatus.TOKEN_UNVALID);
        }

        filterChain.doFilter(request, response);
    }


}

