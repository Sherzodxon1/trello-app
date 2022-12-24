package uzum.trelloapp.dto.gr;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrDelDTO {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("username")
    private String username;

}
