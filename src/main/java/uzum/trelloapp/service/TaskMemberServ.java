package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.pr.PrMemberAddDTO;
import uzum.trelloapp.dto.pr.PrMemberDTO;
import uzum.trelloapp.dto.task.TaskMemberAddDTO;
import uzum.trelloapp.dto.task.TaskMemberDTO;

import javax.transaction.Transactional;

public interface TaskMemberServ {
    @Transactional
    ResponseEntity<ResponseData<TaskMemberDTO>> addMember(TaskMemberAddDTO dto);
}
