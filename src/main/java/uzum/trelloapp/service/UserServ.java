package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.auth.ReqLoginDTO;
import uzum.trelloapp.dto.auth.ReqRefreshTokenDTO;
import uzum.trelloapp.dto.auth.ReqRegDTO;
import uzum.trelloapp.dto.auth.SessionDTO;
import uzum.trelloapp.dto.user.*;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.exception.AlreadyExistsException;
import uzum.trelloapp.exception.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface UserServ {
    ResponseEntity<ResponseData<List<UserDTO>>> getAll();

    ResponseEntity<ResponseData<UserDTO>> get(UUID uuid);

    ResponseEntity<ResponseData<UserDTO>> add(UserCrDTO dto);

    ResponseEntity<ResponseData<UserDTO>> edit(UserUpDTO dto);

    ResponseEntity<ResponseData<UserDTO>> me(UserMeDTO dto);

    ResponseEntity<ResponseData<UserDTO>> delete(UserDelDTO dto);

    ResponseEntity<ResponseData<UserDTO>> changePassword(UserChPDTO dto);

    User findByUuid(UUID uuid) throws UserNotFoundException;

    ResponseEntity<SessionDTO> login(ReqLoginDTO req, HttpServletRequest httpReq);

    ResponseEntity<SessionDTO> refreshToken(ReqRefreshTokenDTO req, HttpServletRequest httpReq);

    User findByPhone(String phone) throws UsernameNotFoundException;

    Role saveRole(Role role);

    void attachRoleToUser(String phone, String roleName);

    ResponseEntity<ResponseData<UserDTO>> registration(ReqRegDTO req, HttpServletRequest httpReq) throws AlreadyExistsException;

    User checkUser(UUID uuid);

    User findByUsername(String username) throws UsernameNotFoundException;
}
