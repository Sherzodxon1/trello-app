package uzum.trelloapp.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskPositionDTO {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("column_id")
    private Long columnId;

}
