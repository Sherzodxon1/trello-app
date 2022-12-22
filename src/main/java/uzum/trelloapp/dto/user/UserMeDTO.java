package uzum.trelloapp.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
@JsonPropertyOrder({"uuid", "password"})
public class UserMeDTO {

    @JsonProperty("uuid")
    protected UUID uuid;
    @JsonProperty("password")
    private String password;

}
