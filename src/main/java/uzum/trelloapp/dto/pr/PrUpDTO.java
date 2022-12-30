package uzum.trelloapp.dto.pr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class PrUpDTO {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("name")
    private String name;

    @JsonProperty("username")
    private String username;

    @JsonProperty("description")
    private String description;

    @JsonProperty("ownerId")
    private Long ownerId;

    @JsonProperty("type")
    private String type;

}
