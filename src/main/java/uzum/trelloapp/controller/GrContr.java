package uzum.trelloapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uzum.trelloapp.base.BaseURI;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.*;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.service.GrServ;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API + BaseURI.V1 + BaseURI.GROUP)
@Api(value = "GROUP APIS", description = "For the GROUP category")
public class GrContr {

    private final GrServ serv;

    @ApiOperation(value = "Get all GROUPS")
    @GetMapping(BaseURI.GET + BaseURI.ALL)
    public ResponseEntity<ResponseData<List<GrDTO>>> getAll() {
        return serv.getAll();
    }

    @ApiOperation(value = "Get GROUP by UUID")
    @GetMapping(BaseURI.GET)
    public ResponseEntity<ResponseData<GrDTO>> get(@Valid @RequestBody GrGetDTO dto) {
        return serv.get(dto);
    }

    @ApiOperation(value = "Create GROUP")
    @PostMapping(BaseURI.ADD)
    public ResponseEntity<ResponseData<GrDTO>> add(@Valid @RequestBody GrCrDTO dto) throws UserNotFoundException {
        return serv.add(dto);
    }

    @ApiOperation(value = "Edit GROUP")
    @PostMapping(BaseURI.EDIT)
    public ResponseEntity<ResponseData<GrDTO>> edit(@Valid @RequestBody GrUpDTO dto) {
        return serv.edit(dto);
    }

    public ResponseEntity<ResponseData<GrDTO>> delete(@Valid @RequestBody GrDelDTO dto) {
        return serv.delete(dto);
    }

}
