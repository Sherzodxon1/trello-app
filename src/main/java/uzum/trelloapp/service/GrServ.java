package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.*;
import uzum.trelloapp.entity.Group;
import uzum.trelloapp.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface GrServ {
    ResponseEntity<ResponseData<List<GrDTO>>> getAll();

    ResponseEntity<ResponseData<GrDTO>> get(GrGetDTO dto);

    ResponseEntity<ResponseData<GrDTO>> add(GrCrDTO dto) throws UserNotFoundException;

    ResponseEntity<ResponseData<GrDTO>> edit(GrUpDTO dto);

    ResponseEntity<ResponseData<GrDTO>> delete(GrDelDTO dto);

    Group checkGroup(UUID uuid);

    boolean isMember(UUID groupUuid, UUID userUuid);
}
