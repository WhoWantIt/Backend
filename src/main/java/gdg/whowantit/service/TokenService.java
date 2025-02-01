package gdg.whowantit.service;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.dto.TokenResponse;
import gdg.whowantit.entity.RefreshToken;
import gdg.whowantit.entity.Role;
import gdg.whowantit.repository.RefreshTokenRepository;
import gdg.whowantit.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {
    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    public void logout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // Refresh Token 삭제
        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        refreshTokenRepository.deleteByEmail(email);

        SecurityContextHolder.clearContext();
    }

    // Access Token 생성
    public String generateAccessToken(String email, Role role) {
        return jwtUtil.generateAccessToken(email, role);
    }

    // Refresh Token 생성
    public String generateRefreshToken(String email, Role role) {
        String refreshToken = jwtUtil.generateRefreshToken(email, role);
        saveOrUpdateRefreshToken(email, refreshToken);
        return refreshToken;
    }

    // Refresh Token 저장 또는 업데이트
    private void saveOrUpdateRefreshToken(String email, String refreshToken) {
        refreshTokenRepository.findByEmail(email)
                .ifPresentOrElse(
                        token -> {
                            token.setToken(refreshToken);
                            token.setExpiration(Instant.now().plusMillis(1000 * 60 * 60 * 24 * 7).getEpochSecond());
                            refreshTokenRepository.save(token);
                        },
                        () -> {
                            RefreshToken newToken = new RefreshToken();
                            newToken.setEmail(email);
                            newToken.setToken(refreshToken);
                            newToken.setExpiration(Instant.now().plusMillis(1000 * 60 * 60 * 24 * 7).getEpochSecond());
                            refreshTokenRepository.save(newToken);
                        }
                );
    }


    // Refresh Token을 이용한 Access Token 재발급
    public TokenResponse regenerateAccessToken(HttpServletRequest request) {
        // Refresh Token 검증

        System.out.println("TokenService.regenerateAccessToken1");
        String refreshToken = (String) request.getAttribute("refreshToken");
        System.out.println("TokenService.regenerateAccessToken2");

        if (!jwtUtil.validateToken(refreshToken, "refresh")) {
            throw new TempHandler(ErrorStatus.TOKEN_UNVALID);
        }
        System.out.println("TokenService.regenerateAccessToken3");
        // Refresh Token에서 이메일 추출
        String email = jwtUtil.getEmailFromToken(refreshToken);
        Role role = jwtUtil.getRoleFromToken(refreshToken);
        System.out.println("validate는 통과했다" + email + role);
        // 새 Access Token 생성 및 반환
        System.out.println("TokenService.regenerateAccessToken4");
        System.out.println(email + role);
        String newAccessToken = jwtUtil.generateAccessToken(email, role);

// ✅ 새로운 Access Token을 SecurityContext에 반영
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(email, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new TokenResponse(refreshToken, newAccessToken);
    }
}