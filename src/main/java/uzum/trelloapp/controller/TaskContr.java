package uzum.trelloapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uzum.trelloapp.base.BaseURI;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.task.TaskDTO;
import uzum.trelloapp.dto.task.TaskPositionDTO;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.service.impl.TaskServ;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API + BaseURI.V1 + BaseURI.TASK)
@Api(value = "TASK APIS", description = "For the TASK category")
public class TaskContr {

    private final TaskServ serv;

    @ApiOperation("Insert position at task")
    @PostMapping(BaseURI.INSERT + BaseURI.POSITION)
    public ResponseEntity<ResponseData<TaskDTO>> draggable(@Valid @RequestBody TaskPositionDTO dto) throws UserNotFoundException {
        return serv.draggable(dto);
    }
}
