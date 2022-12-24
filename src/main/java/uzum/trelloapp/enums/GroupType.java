package uzum.trelloapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GroupType {

    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC"),
    OPERATIONS("OPERATIONS"),
    ENGINEERING_IT("ENGINEERING_IT"),
    MARKETING("MARKETING"),
    PERSONAL_MANAGEMENT("PERSONAL_MANAGEMENT"),
    EDUCATION("EDUCATION"),
    SALES_CRM("SALES_CRM"),
    SMALL_BUSINESS("SMALL_BUSINESS"),
    UNKNOWN("UNKNOWN");

    private final String name;

    public static GroupType getByName(final String name) {
        return Arrays.stream(GroupType.values())
                .filter(group -> group.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
