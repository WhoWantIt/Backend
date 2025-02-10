package gdg.whowantit.util;

import gdg.whowantit.apiPayload.code.status.ErrorStatus;
import gdg.whowantit.apiPayload.exception.handler.TempHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {

    // ✅ 현재 로그인된 사용자의 Authentication 객체 가져오기
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    // ✅ 현재 로그인된 사용자의 이메일 가져오기
    public static String getCurrentUserEmail() {
        Authentication authentication = getAuthentication();

        if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
            throw new TempHandler(ErrorStatus.TOKEN_EXPIRED); // 🔥 인증되지 않은 사용자는 예외 발생
        }

        return authentication.getName(); // ✅ 이메일 반환
    }


}