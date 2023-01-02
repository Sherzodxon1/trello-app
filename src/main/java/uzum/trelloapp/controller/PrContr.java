package uzum.trelloapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uzum.trelloapp.base.BaseURI;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.pr.*;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.service.PrServ;
import uzum.trelloapp.service.impl.PrMemberServ;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API + BaseURI.V1 + BaseURI.PROJECT)
@Api(value = "PROJECT APIS", description = "For the PROJECT category")
public class PrContr {

    private final PrServ serv;
    private final PrMemberServ prMemberServ;

    @ApiOperation("Get all PROJECTS ")
    @GetMapping(BaseURI.GET + BaseURI.ALL)
    public ResponseEntity<ResponseData<List<PrDTO>>> getAll() {
        return serv.getAll();
    }

    @ApiOperation("Get PROJECT by UUID")
    @GetMapping(BaseURI.GET)
    public ResponseEntity<ResponseData<PrDTO>> get(@Valid @RequestBody PrGetDTO dto) {
        return serv.get(dto);
    }

    @ApiOperation("Add PROJECT")
    @PostMapping(BaseURI.ADD)
    public ResponseEntity<ResponseData<PrDTO>> add(@Valid @RequestBody PrCrDTO dto) throws UserNotFoundException {
        return serv.add(dto);
    }

    @ApiOperation("Edit PROJECT")
    @PostMapping(BaseURI.EDIT)
    public ResponseEntity<ResponseData<PrDTO>> edit(@Valid @RequestBody PrUpDTO dto) {
        return serv.edit(dto);
    }

    @ApiOperation("Delete PROJECT")
    @PostMapping(BaseURI.DELETE)
    public ResponseEntity<ResponseData<PrDTO>> delete(@Valid @RequestBody PrDelDTO dto) {
        return serv.delete(dto);
    }

    @PostMapping(BaseURI.ADD + BaseURI.MEMBER)
    public ResponseEntity<ResponseData<PrMemberDTO>> addMember(@Valid @RequestBody PrMemberAddDTO dto) {
        return prMemberServ.addMember(dto);
    }

}
