package uzum.trelloapp.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import uzum.trelloapp.dto.pr.PrDTO;
import uzum.trelloapp.entity.ProjectColumn;

import java.util.UUID;

@Getter
@Setter
@ToString
public class TaskUpDTO {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("project_id")
    private PrDTO projectId;

    @JsonProperty("project_column_id")
    private ProjectColumn projectColumn;

    @JsonProperty("position")
    private Integer position;
}
