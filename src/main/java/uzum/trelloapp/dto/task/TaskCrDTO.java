package uzum.trelloapp.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uzum.trelloapp.dto.pr.PrDTO;
import uzum.trelloapp.entity.ProjectColumn;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskCrDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("project_id")
    private PrDTO projectId;

    @JsonProperty("project_column_id")
    private ProjectColumn projectColumn;

    @JsonProperty("position")
    private Integer position;
}
