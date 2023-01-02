package uzum.trelloapp.dto.pr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uzum.trelloapp.dto.gr.GrDTO;
import uzum.trelloapp.dto.user.UserDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrMemberDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("project")
    private PrDTO project;

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
