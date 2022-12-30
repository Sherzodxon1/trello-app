package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.pr.*;
import uzum.trelloapp.entity.Project;
import uzum.trelloapp.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface PrServ {
    ResponseEntity<ResponseData<List<PrDTO>>> getAll();

    ResponseEntity<ResponseData<PrDTO>> get(PrGetDTO dto);

    ResponseEntity<ResponseData<PrDTO>> add(PrCrDTO dto) throws UserNotFoundException;

    ResponseEntity<ResponseData<PrDTO>> edit(PrUpDTO dto);

    ResponseEntity<ResponseData<PrDTO>> delete(PrDelDTO dto);

    Project checkProject(UUID uuid);

    boolean isMember(UUID groupUuid, UUID userUuid);
}
