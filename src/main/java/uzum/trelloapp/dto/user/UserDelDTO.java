package uzum.trelloapp.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@JsonIgnoreProperties("deleted")
public class UserDelDTO {
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("password")
    private String password;

    @JsonProperty("deleted")
    private boolean deleted = true;

}
