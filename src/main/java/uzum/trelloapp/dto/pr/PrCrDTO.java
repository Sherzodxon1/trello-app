package uzum.trelloapp.dto.pr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrCrDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("groupId")
    private Long groupId;

    @JsonProperty("type")
    private String type;

}
