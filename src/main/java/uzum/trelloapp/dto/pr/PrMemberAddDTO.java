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
public class PrMemberAddDTO {

    @JsonProperty("host")
    private UUID host; // taklif qilayotgan user

    @JsonProperty("guest")
    private UUID guest; // taklif qilinayotgan user

    @JsonProperty("project")
    private UUID project;

}
