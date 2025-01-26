package gdg.whowantit.service;

import gdg.whowantit.entity.RefreshToken;
import gdg.whowantit.repository.RefreshTokenRepository;
import gdg.whowantit.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final RefreshTokenRepository refreshTokenRepository;

    // Access Token 생성
    public String generateAccessToken(String email) {
        return jwtUtil.generateAccessToken(email);
    }

    // Refresh Token 생성
    public String generateRefreshToken(String email) {
        String refreshToken = jwtUtil.generateRefreshToken(email);

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
            jwtUtil.validateToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Refresh Token을 이용한 Access Token 재발급
    public String regenerateAccessToken(String refreshToken) {
        if (!validateToken(refreshToken)) {
            throw new IllegalArgumentException("Invalid Refresh Token");
        }

        String email = jwtUtil.validateToken(refreshToken).getSubject();
        return generateAccessToken(email);
    }
}