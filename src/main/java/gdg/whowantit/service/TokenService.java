package gdg.whowantit.service;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.entity.RefreshToken;
import gdg.whowantit.entity.Role;
import gdg.whowantit.repository.RefreshTokenRepository;
import gdg.whowantit.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Transactional
public class TokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public void logout() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED);
        }

        // Refresh Token 삭제
        String email = authentication.getName(); // 현재 로그인된 사용자의 이메일
        refreshTokenRepository.deleteByEmail(email);
    }

    // Access Token 생성
    public String generateAccessToken(String email, Role role) {
        return JwtUtil.generateAccessToken(email, role);
    }

    // Refresh Token 생성
    public String generateRefreshToken(String email) {
        String refreshToken = JwtUtil.generateRefreshToken(email);
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

    // Access Token 또는 Refresh Token 검증
    public boolean validateToken(String token) {
        try {
            JwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Refresh Token을 이용한 Access Token 재발급
    public String regenerateAccessToken(String refreshToken) {
        // Refresh Token 검증
        if (!JwtUtil.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        // Refresh Token에서 이메일 추출
        String email = JwtUtil.getEmailFromToken(refreshToken);
        Role role = JwtUtil.getRoleFromToken(refreshToken);
        // 새 Access Token 생성 및 반환
        return JwtUtil.generateAccessToken(email, role);
    }
}