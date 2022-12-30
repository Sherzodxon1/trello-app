package uzum.trelloapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum RoleType {

    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER"),
    UNKNOWN("ROLE_UNKNOWN");

    private final String description;


    public static RoleType getByName(final String name) {
        return Arrays.stream(RoleType.values())
                .filter(operationType -> operationType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
