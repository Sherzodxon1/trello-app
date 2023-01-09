package uzum.trelloapp.dto.task;

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
public class TaskMemberAddDTO {

    @JsonProperty("host")
    private UUID host; // taklif qilayotgan user

    @JsonProperty("guest")
    private UUID guest; // taklif qilinayotgan user

    @JsonProperty("task")
    private UUID task;

}
