package uzum.trelloapp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uzum.trelloapp.base.BaseURI;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.AttachRoleToUserDTO;
import uzum.trelloapp.dto.user.*;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.service.UserServ;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API + BaseURI.V1 + BaseURI.USER)
@Api(value = "USER APIS", description = "For the USER category")
public class UserContr {

    private final UserServ serv;

    @PostMapping(BaseURI.ROLE + BaseURI.ADD)
    private ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(serv.saveRole(role), HttpStatus.CREATED);
    }

    @PostMapping(BaseURI.ATTACH + BaseURI.ROLE + BaseURI.TO + BaseURI.USER)
    private ResponseEntity<?> attachRoleToUser(@RequestBody AttachRoleToUserDTO dto) {
        serv.attachRoleToUser(dto.getPhone(), dto.getRoleName());
        return ResponseEntity.ok().build();
    }

    //    @PreAuthorize("hasAuthority('GET_ALL_USERS')")
    @ApiOperation(value = "Get all USERS")
    @GetMapping(BaseURI.GET + BaseURI.ALL)
    public ResponseEntity<ResponseData<List<UserDTO>>> getAll() {
        return serv.getAll();
    }

    //    @PreAuthorize("hasAuthority('GET_USER')")
    @ApiOperation(value = "Get USER by UUID")
    @GetMapping(BaseURI.GET)
    public ResponseEntity<ResponseData<UserDTO>> get(@RequestParam(value = "uuid") UUID uuid) {
        return serv.get(uuid);
    }

    //    @PreAuthorize("hasAuthority('ADD_USER')")
    @ApiOperation(value = "Create USER")
    @PostMapping(BaseURI.ADD)
    public ResponseEntity<ResponseData<UserDTO>> add(@Valid @RequestBody UserCrDTO dto) {
        return serv.add(dto);
    }

    //    @PreAuthorize("hasAuthority('EDIT_USER')")
    @ApiOperation(value = "Update USER")
    @PostMapping(BaseURI.EDIT)
    public ResponseEntity<ResponseData<UserDTO>> edit(@Valid @RequestBody UserUpDTO dto) {
        return serv.edit(dto);
    }

    //    @PreAuthorize("hasAuthority('DELETE_USER')")
    @ApiOperation(value = "Delete USER")
    @PostMapping(BaseURI.DELETE)
    public ResponseEntity<ResponseData<UserDTO>> delete(@Valid @RequestBody UserDelDTO dto) {
        return serv.delete(dto);
    }

    //    @PreAuthorize("hasAuthority('USER_ME')")
    @ApiOperation(value = "USER ME")
    @PostMapping(BaseURI.ME)
    public ResponseEntity<ResponseData<UserDTO>> me(@Valid @RequestBody UserMeDTO dto) {
        return serv.me(dto);
    }

    //    @PreAuthorize("hasAuthority('CHANGE_PASSWORD')")
    @ApiOperation(value = "CHANGE PASSWORD")
    @PostMapping(BaseURI.CHANGE_PASSWORD)
    public ResponseEntity<ResponseData<UserDTO>> changePassword(@Valid @RequestBody UserChPDTO dto) {
        return serv.changePassword(dto);
    }

}
