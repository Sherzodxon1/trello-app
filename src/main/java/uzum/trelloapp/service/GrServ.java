package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.GrCrDTO;
import uzum.trelloapp.dto.gr.GrDTO;
import uzum.trelloapp.dto.gr.GrDelDTO;
import uzum.trelloapp.dto.gr.GrUpDTO;
import uzum.trelloapp.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface GrServ {
    ResponseEntity<ResponseData<List<GrDTO>>> getAll();

    ResponseEntity<ResponseData<GrDTO>> get(UUID uuid);

    ResponseEntity<ResponseData<GrDTO>> add(GrCrDTO dto) throws UserNotFoundException;

    ResponseEntity<ResponseData<GrDTO>> edit(GrUpDTO dto);

    ResponseEntity<ResponseData<GrDTO>> delete(GrDelDTO dto);

}
