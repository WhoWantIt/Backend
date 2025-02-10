package gdg.whowantit.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class StringListUtil {

    public static String listToString(List<String> list) {
        return Optional.ofNullable(list)
                .map(l -> String.join(",", l))
                .orElse(""); // list가 null이면 빈 문자열 반환
    }

    public static List<String> stringToList(String str) {
        return Optional.ofNullable(str)
                .filter(s -> !s.isEmpty()) // 빈 문자열이면 빈 리스트 반환
                .map(s -> Arrays.asList(s.split(",")))
                .orElse(Collections.emptyList());
    }

}
