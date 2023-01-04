package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.TaskDTO;
import uzum.trelloapp.dto.task.TaskPositionDTO;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.Task;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.helper.UserSession;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.repo.TaskRepo;
import uzum.trelloapp.service.TaskServ;
import uzum.trelloapp.service.UserServ;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServImpl implements TaskServ {

    private final TaskRepo repo;
    private final UserServ userServ;
    private final UserSession session;

    @Override
    public ResponseEntity<ResponseData<TaskDTO>> draggable(TaskPositionDTO dto) throws UserNotFoundException {
        User user = userServ.findByUuid(session.getUser().getUuid());
        Task task = this.checkTask(dto.getUuid());
        for (Role role : user.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                task.setProjectColumnId(dto.getColumnId());
                repo.saveAndFlush(task);
            } else {
                log.warn("Sizda admin huquqlari yo'q !!!");
                return ResponseData.notFoundData("You do not have admin rights!");
            }
        }
        return ResponseData.notFoundData("User role not defined !!!");
    }

    @Override
    public Task checkTask(UUID uuid) {
        Optional<Task> optional = repo.findByUuid(uuid);
        if (optional.isEmpty()) {
            log.error("Task uuid, {} bo'yicha ma'lumot topilmadi", uuid);
            throw new RuntimeException("Task not found!!!");
        }
        Task task = optional.get();
        if (!task.isActive()) {
            log.error("Task uuid, {} bo'yicha faol emas!", uuid);
            throw new RuntimeException("This task is not active!!!");
        }
        return task;
    }
}
