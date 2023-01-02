package uzum.trelloapp.dto.task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uzum.trelloapp.dto.pr.PrDTO;
import uzum.trelloapp.entity.ProjectColumn;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "uuid", "active", "deleted", "createdAt", "updatedAt"})
public class TaskDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("project_id")
    private PrDTO projectId;

    @JsonProperty("project_column_id")
    private ProjectColumn projectColumn;

    @JsonProperty("position")
    private Integer position;

    @JsonProperty("active")
    private boolean active;

    @JsonProperty("deleted")
    private boolean deleted;

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("createdAt")
    protected LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    protected LocalDateTime updatedAt;
}
