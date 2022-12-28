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
public class GrMemberAddDTO {

    @JsonProperty("host")
    private UUID host; // taklif qilayotgan user

    @JsonProperty("guest")
    private UUID guest; // taklif qilinayotgan user

    @JsonProperty("group")
    private UUID group;

}
