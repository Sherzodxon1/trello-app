package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.*;
import uzum.trelloapp.entity.*;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.helper.UserSession;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.TaskMap;
import uzum.trelloapp.repo.TaskMemberRepo;
import uzum.trelloapp.repo.TaskRepo;
import uzum.trelloapp.service.PrServ;
import uzum.trelloapp.service.TaskServ;
import uzum.trelloapp.service.UserServ;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServImpl implements TaskServ {

    private final TaskRepo repo;
    private final TaskMap mapper;
    private final TaskMemberRepo tMemRepo;
    private final PrServ prServ;
    private final UserServ userServ;
    private final UserSession session;

    @Override
    public ResponseEntity<ResponseData<List<TaskDTO>>> getAll() {
        List<Task> list = repo.findAllByOwnerUuid(session.getUser().getUuid());
        if (!Utils.isEmpty(list)) {
            List<TaskDTO> dtoList = new ArrayList<>();
            list.forEach(task -> dtoList.add(mapper.toDto(task)));
            log.info("Barcha tasklar ro'yxati olindi");
            return ResponseData.success200(dtoList);
        } else {
            log.warn("Tasklar ro'yxati topilmadi!");
            return ResponseData.notFoundData("Tasks are not found !!!");
        }
    }

    @Override
    public ResponseEntity<ResponseData<TaskDTO>> get(TaskGetDTO dto) {
        User user = userServ.checkUser(dto.getUser());
        Task task = this.checkTask(dto.getTask());
        for (Role role : user.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                Optional<Task> optional = repo.findByUuid(task.getUuid());
                if (Utils.isEmpty(optional)) {
                    log.error("Task uuid, {} bo'yicha ma'lumot topilmadi", task.getUuid());
                    return ResponseData.notFoundData("Task not found !!!");
                }
                log.info("Task uuid, {} bo'yicha ma'lumot olindi", task.getUuid());
                return ResponseData.success200(mapper.toDto(optional.get()));
            } else {
                boolean isMember = this.isMember(task.getUuid(), user.getUuid());
                if (!isMember) {
                    log.warn("Siz bu taskga a'zo emassiz !!!");
                    return ResponseData.notFoundData("You are not member of this task !!!");
                } else {
                    Optional<Task> optional = repo.findByUuid(task.getUuid());
                    if (Utils.isEmpty(optional)) {
                        log.error("Task uuid, {} bo'yicha ma'lumot topilmadi", task.getUuid());
                        ResponseData.notFoundData("Task not found !!!");
                    }
                    log.info("Task uuid, {} bo'yicha ma'lumot olindi", task.getUuid());
                    return ResponseData.success200(mapper.toDto(optional.get()));
                }
            }
        }
        return ResponseData.notFoundData("User role not defined !!!");
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<TaskDTO>> add(TaskCrDTO dto) throws UserNotFoundException {
        User user = userServ.findByUuid(session.getUser().getUuid());
        if (Utils.isEmpty(user)) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", session.getUser().getUuid());
            return ResponseData.notFoundData("User not found !!!");
        } else if (!user.isActive()) {
            log.error("User uuid, {} bo'yicha faol emas!", session.getUser().getUuid());
            return ResponseData.inActive("This User's status is inactive !!!");
        }

        Project project = prServ.checkProject(dto.getProjectId());
        Task task = mapper.toEntity(dto);

        String link = "https://trello.com/" + "c/" + UUID.randomUUID();
        String username = task.getName().toLowerCase() + System.currentTimeMillis();

        task.setProjectId(project.getId());
        task.setUsername(username.replaceAll("\\s", ""));
        task.setLink(link.replaceAll("\\s", ""));
        repo.save(task);
        log.info("Yangi task - {} saqlandi", task.getName());

        Task memberTask = repo.findByUsername(task.getUsername());
        log.info("Task username, {} bo'yicha ma'lumot olindi", task.getUsername());

        TaskMembers taskMembers = new TaskMembers();
        taskMembers.setTaskId(memberTask.getId());
        taskMembers.setUserId(task.getOwnerId());
        log.info("Yangi task member id -> {} bo'yicha - task id -> {} ga biriktirildi", taskMembers.getUserId(), taskMembers.getTaskId());
        tMemRepo.save(taskMembers);

        return ResponseData.success201(mapper.toDto(task));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<TaskDTO>> edit(TaskUpDTO dto) {
        Optional<Task> optional = repo.findByUuid(dto.getUuid());
        if (Utils.isEmpty(optional)) {
            log.error("Task uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("Task not found !!!");
        }
        Task task = optional.get();
        if (!task.isActive()) {
            log.error("Project uuid, {} bo'yicha faol emas!", dto.getUuid());
            return ResponseData.inActive("This task is not active !!!");
        } else if (!Utils.isOwner(task.getOwnerId(), dto.getOwnerId())) {
            log.error("Sizda administrator huquqlari yo'q!");
            return ResponseData.inActive("You do not have admin rights !");
        }
        task = mapper.toEntity(task, dto);
        repo.save(task);
        log.info("Task {} - ma'lumotlari yangilandi!", task.getUsername());
        return ResponseData.success202(mapper.toDto(task));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<TaskDTO>> delete(TaskDelDTO dto) {
        Optional<Task> optional = repo.findByUuid(dto.getUuid());
        if (Utils.isEmpty(optional)) {
            log.error("Task uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            ResponseData.notFoundData("Task not found !!!");
        }

        Task task = optional.get();
        if (task.isDeleted()) {
            log.error("Task uuid, {} bo'yicha oldin o'chirilgan", dto.getUuid());
            return ResponseData.isDeleted("This task was previously disabled !!!");
        } else if (!task.getUsername().equals(dto.getUsername())) {
            log.error("Task username, {} bo'yicha username mos emas!", dto.getUsername());
            return ResponseData.errorStatus("Username is incorrect", HttpStatus.NOT_FOUND);
        }

        task.setDeleted(true);
        task.setActive(false);
        task.setUsername(task.getUsername() + "_isDel");
        repo.save(task);
        log.info("Task uuid, {} bo'yicha o'chirildi!", dto.getUuid());
        return ResponseData.success200(mapper.toDto(task));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<TaskDTO>> draggable(TaskPositionDTO dto) throws UserNotFoundException {
        User user = userServ.findByUuid(session.getUser().getUuid());
        Task task = this.checkTask(dto.getUuid());
        for (Role role : user.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                task.setColumnId(dto.getColumnId());
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

    @Override
    public boolean isMember(UUID task, UUID user) {
        Optional<TaskMembers> taskMembers = tMemRepo.findByTaskUuidAndUserUuid(task, user);
        return taskMembers.isPresent();
    }
}
