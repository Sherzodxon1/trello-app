package uzum.trelloapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uzum.trelloapp.base.BaseURI;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.*;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.service.TaskMemberServ;
import uzum.trelloapp.service.TaskServ;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API + BaseURI.V1 + BaseURI.TASK)
@Api(value = "TASK APIS", description = "For the TASK category")
public class TaskContr {

    private final TaskServ serv;
    private final TaskMemberServ tMemberServ;

    @ApiOperation("Get All TASKS")
    @GetMapping(BaseURI.GET + BaseURI.ALL)
    public ResponseEntity<ResponseData<List<TaskDTO>>> getAll() {
        return serv.getAll();
    }

    @ApiOperation("Get TASK by UUID")
    @GetMapping(BaseURI.GET)
    public ResponseEntity<ResponseData<TaskDTO>> get(@Valid @RequestBody TaskGetDTO dto) {
        return serv.get(dto);
    }

    @Transactional
    @ApiOperation("Add TASK")
    @PostMapping(BaseURI.ADD)
    public ResponseEntity<ResponseData<TaskDTO>> add(@Valid @RequestBody TaskCrDTO dto) throws UserNotFoundException {
        return serv.add(dto);
    }

    @Transactional
    @ApiOperation("Edit TASK")
    @PostMapping(BaseURI.EDIT)
    public ResponseEntity<ResponseData<TaskDTO>> edit(@Valid @RequestBody TaskUpDTO dto) {
        return serv.edit(dto);
    }

    @Transactional
    @ApiOperation(("Delete TASK"))
    @PostMapping(BaseURI.DELETE)
    public ResponseEntity<ResponseData<TaskDTO>> delete(@Valid @RequestBody TaskDelDTO dto) {
        return serv.delete(dto);
    }

    @Transactional
    @ApiOperation("Add member to TASK")
    @PostMapping(BaseURI.ADD + BaseURI.MEMBER)
    public ResponseEntity<ResponseData<TaskMemberDTO>> addMember(@Valid @RequestBody TaskMemberAddDTO dto) {
        return tMemberServ.addMember(dto);
    }

    @Transactional
    @ApiOperation("Insert position at task")
    @PostMapping(BaseURI.INSERT + BaseURI.POSITION)
    public ResponseEntity<ResponseData<TaskDTO>> draggable(@Valid @RequestBody TaskPositionDTO dto) throws UserNotFoundException {
        return serv.draggable(dto);
    }
}
