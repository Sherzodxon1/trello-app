package uzum.trelloapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uzum.trelloapp.base.BaseURI;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.auth.ReqLoginDTO;
import uzum.trelloapp.dto.auth.ReqRefreshTokenDTO;
import uzum.trelloapp.dto.auth.ReqRegDTO;
import uzum.trelloapp.dto.auth.SessionDTO;
import uzum.trelloapp.dto.user.UserDTO;
import uzum.trelloapp.exception.AlreadyExistsException;
import uzum.trelloapp.service.UserServ;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping(BaseURI.API1 + BaseURI.PUBLIC)
public class AuthController {

    private final UserServ serv;

    @PostMapping(BaseURI.REGISTRATION)
    public ResponseEntity<ResponseData<UserDTO>> registration(@RequestBody ReqRegDTO req, HttpServletRequest httpReq) throws AlreadyExistsException {
        return serv.registration(req, httpReq);
    }

    @PostMapping(BaseURI.LOGIN)
    public ResponseEntity<SessionDTO> login(@RequestBody ReqLoginDTO req, HttpServletRequest httpReq) {
        return serv.login(req, httpReq);
    }

    @PostMapping(BaseURI.TOKEN + BaseURI.REFRESH)
    public ResponseEntity<SessionDTO> refreshToken(@RequestBody ReqRefreshTokenDTO req, HttpServletRequest httpReq) {
        return serv.refreshToken(req, httpReq);
    }

}
