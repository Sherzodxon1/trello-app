package uzum.trelloapp.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UserUpDTO {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("firstName")
    private String firstName;

    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("username")
    private String username;

    @JsonProperty("phone")
    private String phone;

}
