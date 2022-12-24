package uzum.trelloapp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ProjectType {

    PRIVATE("PRIVATE"),
    PUBLIC("PUBLIC"),
    WORK_SPACES("WORK_SPACES"),
    UNKNOWN("UNKNOWN");

    private final String description;


    public static ProjectType getByName(final String name) {
        return Arrays.stream(ProjectType.values())
                .filter(operationType -> operationType.name().equalsIgnoreCase(name))
                .findFirst()
                .orElse(UNKNOWN);
    }

}
