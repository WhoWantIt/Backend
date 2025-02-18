package gdg.whowantit.util;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import gdg.whowantit.entity.RefreshToken;
import gdg.whowantit.entity.Role;
import gdg.whowantit.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private static Key key;
    private final RefreshTokenRepository refreshTokenRepository; // ✅ Repository 주입
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60; // 1 시간
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    // 🔥 SECRET_KEY를 환경 변수에서 불러오기
    // 🔥 환경 변수로부터 SECRET_KEY 설정
    public void init(String secretKey) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("🚨 JWT Secret Key가 설정되지 않았습니다.");
        }
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }
    // ✅ Access Token 생성 (Role을 문자열로 저장)
    public String generateAccessToken(String email, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name()); // 🔥 Enum을 String으로 변환하여 저장
        claims.put("tokenType", "access");  // ✅ 토큰 타입 추가

        System.out.println(claims.get("role"));
        System.out.println(claims.get("tokenType"));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

    }

    // ✅ Refresh Token 생성
    public String generateRefreshToken(String email, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tokenType", "refresh");  // ✅ 토큰 타입 추가
        claims.put("role", role.name());
        System.out.println(claims.get("tokenType"));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰 검증 (예외 메시지 포함)
    public boolean validateToken(String token, String expectedType) {

        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

            // ✅ 토큰 타입 검증 (Access 또는 Refresh)
            System.out.println("expectedType: " + expectedType);
            System.out.println("token = " + token);

            String tokenType = (String) claims.get("tokenType");
            System.out.println("expectedType: " + expectedType);
            System.out.println("token = " + token);
            System.out.println("tokenType = " + tokenType);
            if (!expectedType.trim().equals(tokenType.trim())) {
                System.out.println("❌ 예상된 토큰 타입이 아닙니다. (예상: " + expectedType + ", 실제: " + tokenType + ")");
                return false;
            }
            if ("refresh".equals(expectedType)) {
                String email = claims.getSubject(); // ✅ Refresh Token에서 email 추출
                Optional<RefreshToken> storedToken = refreshTokenRepository.findByEmail(email);
                System.out.println("1");

                if (storedToken.isEmpty() || !storedToken.get().getToken().equals(token)) {
                    System.out.println("❌ Refresh Token이 DB와 일치하지 않습니다.");
                    System.out.println("2");
                    return false;
                }
            }
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("JWT가 만료되었습니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 형식입니다.");
        } catch (MalformedJwtException e) {
            System.out.println("JWT가 손상되었습니다.");
        } catch (SignatureException e) {
            System.out.println("JWT 서명이 잘못되었습니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT가 비어 있습니다.");
        }

        System.out.println("3");
        return false;
    }

    // ✅ Claims에서 Role 가져오기 (문자열 변환 후 Enum으로 변환)
    public Role getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        Object roleObject = claims.get("role");
        if (roleObject == null) {
            throw new TempHandler(ErrorStatus.TOKEN_UNVALID);  // ✅ 예외 처리
        }

        return Role.valueOf(claims.get("role").toString()); // 🔥 String으로 변환 후 사용



    }

    // ✅ Claims에서 Email 가져오기
    public String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // ✅ Claims 반환
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // HTTP 요청 헤더에서 토큰 추출
    public String extractToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);  // "Bearer " 제거 후 토큰 반환
        }
        return null;  // 헤더가 없거나 형식이 맞지 않으면 null 반환
    }


    public String getTokenType(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return (String) claims.get("tokenType");  // ✅ 토큰 타입 (access / refresh) 반환
        } catch (Exception e) {
            return null;  // 예외 발생 시 null 반환
        }
    }

}
