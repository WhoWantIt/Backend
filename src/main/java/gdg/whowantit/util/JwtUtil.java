package gdg.whowantit.util;

import gdg.whowantit.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    private static Key key;

    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 15; // 15분
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; // 7일

    // 🔥 SECRET_KEY를 환경 변수에서 불러오기
    // 🔥 환경 변수로부터 SECRET_KEY 설정
    public static void init(String secretKey) {
        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalArgumentException("🚨 JWT Secret Key가 설정되지 않았습니다.");
        }
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(keyBytes);
    }
    // ✅ Access Token 생성 (Role을 문자열로 저장)
    public static String generateAccessToken(String email, Role role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role.name()); // 🔥 Enum을 String으로 변환하여 저장

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ Refresh Token 생성
    public static String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // ✅ 토큰 검증 (예외 메시지 포함)
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
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
        return false;
    }

    // ✅ Claims에서 Role 가져오기 (문자열 변환 후 Enum으로 변환)
    public static Role getRoleFromToken(String token) {
        Claims claims = getClaims(token);
        return Role.valueOf(claims.get("role").toString()); // 🔥 String으로 변환 후 사용
    }

    // ✅ Claims에서 Email 가져오기
    public static String getEmailFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // ✅ Claims 반환
    public static Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
