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

    @JsonProperty("description")
    private String description;

    @JsonProperty("position")
    private Integer position;
}
