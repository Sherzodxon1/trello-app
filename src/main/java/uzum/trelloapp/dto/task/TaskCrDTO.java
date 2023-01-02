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

    @JsonProperty("description")
    private String description;

    @JsonProperty("project")
    private PrDTO project;

    @JsonProperty("column_id")
    private ProjectColumn columnId;

    @JsonProperty("position")
    private Integer position;
}
