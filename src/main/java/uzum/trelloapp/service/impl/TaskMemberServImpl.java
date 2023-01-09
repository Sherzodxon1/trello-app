package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.TaskMemberAddDTO;
import uzum.trelloapp.dto.task.TaskMemberDTO;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.Task;
import uzum.trelloapp.entity.TaskMembers;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.TaskMemberMap;
import uzum.trelloapp.repo.TaskMemberRepo;
import uzum.trelloapp.service.TaskMemberServ;
import uzum.trelloapp.service.TaskServ;
import uzum.trelloapp.service.UserServ;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskMemberServImpl implements TaskMemberServ {

    private final UserServ userServ;
    private final TaskServ tServ;
    private final TaskMemberRepo tMemberRepo;
    private final TaskMemberMap mapper;

    @Transactional
    @Override
    public ResponseEntity<ResponseData<TaskMemberDTO>> addMember(TaskMemberAddDTO dto) {
        User host = userServ.checkUser(dto.getHost());  // check and get user
        User guest = userServ.checkUser(dto.getGuest());  // check and get user
        Task task = tServ.checkTask(dto.getTask()); // check and get project
        for (Role role : host.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                TaskMembers member = new TaskMembers();
                member.setUserId(guest.getId());
                member.setTaskId(task.getId());
                tMemberRepo.save(member);
                log.info("Yangi task member id -> {} bo'yicha - task id -> {} ga biriktirildi", member.getUserId(), member.getTaskId());
                return ResponseData.success201(mapper.toDto(member));
            } else if (Utils.isOwner(task.getOwnerId(), host.getId())) {
                TaskMembers member = new TaskMembers();
                member.setUserId(guest.getId());
                member.setTaskId(task.getId());
                tMemberRepo.save(member);
                log.info("Yangi task member id -> {} bo'yicha - task id -> {} ga biriktirildi", member.getUserId(), member.getTaskId());
                return ResponseData.success201(mapper.toDto(member));
            }
            log.warn("Sizda admin huquqlari yo'q !!!");
            return ResponseData.notFoundData("You do not have admin rights!");
        }
        return ResponseData.notFoundData("User role not defined !!!");
    }

}
