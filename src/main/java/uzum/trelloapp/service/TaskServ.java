package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.*;
import uzum.trelloapp.entity.Task;
import uzum.trelloapp.exception.UserNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

public interface TaskServ {

    ResponseEntity<ResponseData<List<TaskDTO>>> getAll();

    ResponseEntity<ResponseData<TaskDTO>> get(TaskGetDTO dto);

    @Transactional
    ResponseEntity<ResponseData<TaskDTO>> add(TaskCrDTO dto) throws UserNotFoundException;

    @Transactional
    ResponseEntity<ResponseData<TaskDTO>> edit(TaskUpDTO dto);

    @Transactional
    ResponseEntity<ResponseData<TaskDTO>> delete(TaskDelDTO dto);

    @Transactional
    ResponseEntity<ResponseData<TaskDTO>> draggable(TaskPositionDTO dto) throws UserNotFoundException;

    Task checkTask(UUID uuid);

    boolean isMember(UUID task, UUID user);
}
