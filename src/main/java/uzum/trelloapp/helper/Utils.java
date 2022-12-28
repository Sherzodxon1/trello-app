package uzum.trelloapp.helper;

import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public class Utils {

    public static final DateTimeFormatter formatLocalDateTime = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
    public static final DateTimeFormatter formatLocal = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static LocalDateTime toLocalDateTime(String stringTime) {
        return LocalDateTime.parse(stringTime, formatLocalDateTime);
    }

    public static LocalDate toLocalDate(String stringDate) {
        return LocalDate.parse(stringDate, formatLocal);
    }


    public static boolean isEmpty(Object obj) {
        return obj == null;
    }

    public static boolean isEmpty(String str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isEmpty(Map<?, ?> map) {
        return map == null || map.isEmpty();
    }

    public static boolean isPresent(Object obj) {
        return obj != null;
    }

    public static boolean isPresent(String str) {
        return StringUtils.hasText(str);
    }

    public static boolean isPresent(List<?> list) {
        return list != null && !list.isEmpty();
    }

    public static boolean isPresent(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isAdmin(String roleName) {
        //todo superadmin adminga tekshirish
        return "ROLE_SUPER_ADMIN".equals(roleName) || "ROLE_ADMIN".equals(roleName);
    }
}
