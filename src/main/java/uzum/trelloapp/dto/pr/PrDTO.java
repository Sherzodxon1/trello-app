package uzum.trelloapp.dto.pr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uzum.trelloapp.dto.gr.GrDTO;
import uzum.trelloapp.dto.user.UserDTO;
import uzum.trelloapp.enums.ProjectType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"id", "uuid", "active", "deleted", "createdAt", "updatedAt"})
public class PrDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("description")
    private String description;

    @JsonProperty("link")
    private String link;

    @JsonProperty("owner")
    private UserDTO owner;

    @JsonProperty("group")
    private GrDTO group;

    @JsonProperty("type")
    private ProjectType type;

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
