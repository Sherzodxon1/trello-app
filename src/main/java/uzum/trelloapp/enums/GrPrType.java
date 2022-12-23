package uzum.trelloapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum GrPrType {

    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC"),
    WORK_SPACES("WORK_SPACES"),
    UNKNOWN("UNKNOWN");

    private final String description;


    public static GrPrType getByName(final String name) {
        return Arrays.stream(GrPrType.values())
                .filter(operationType -> operationType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
