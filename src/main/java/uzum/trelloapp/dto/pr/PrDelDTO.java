package uzum.trelloapp.dto.pr;

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
public class PrDelDTO {

    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("username")
    private String username;

}
