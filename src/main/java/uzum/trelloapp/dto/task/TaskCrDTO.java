package uzum.trelloapp.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCrDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("projectId")
    private Long projectId;

    @JsonProperty("columnId")
    private Long columnId;

    @JsonProperty("position")
    private Integer position;
}
