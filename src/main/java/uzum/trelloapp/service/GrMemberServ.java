package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.*;
import uzum.trelloapp.entity.Group;
import uzum.trelloapp.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface GrMemberServ {
    ResponseEntity<ResponseData<GrMemberDTO>> addMember(GrMemberAddDTO dto);
}
