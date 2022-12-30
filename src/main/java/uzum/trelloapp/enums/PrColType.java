package uzum.trelloapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum PrColType {

    TODO("TODO", 1),
    DOING("DOING", 2),
    DONE("DONE", 3),
    ARCHIVE("ARCHIVE", 4),
    UNKNOWN("UNKNOWN", 0);

    private final String description;
    private final Integer position;


    public static PrColType getByName(final String name) {
        return Arrays.stream(PrColType.values())
                .filter(operationType -> operationType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
