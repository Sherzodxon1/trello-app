package uzum.trelloapp.dto.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uzum.trelloapp.dto.pr.PrDTO;
import uzum.trelloapp.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskMemberDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("task")
    private TaskDTO task;

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
