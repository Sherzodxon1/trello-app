package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.TaskDTO;
import uzum.trelloapp.dto.task.TaskPositionDTO;
import uzum.trelloapp.entity.Task;
import uzum.trelloapp.exception.UserNotFoundException;

import java.util.UUID;

public interface TaskServ {
    ResponseEntity<ResponseData<TaskDTO>> draggable(TaskPositionDTO dto) throws UserNotFoundException;

    Task checkTask(UUID uuid);
}
