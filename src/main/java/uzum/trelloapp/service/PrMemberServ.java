package uzum.trelloapp.service;

import org.springframework.http.ResponseEntity;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.pr.PrMemberAddDTO;
import uzum.trelloapp.dto.pr.PrMemberDTO;

public interface PrMemberServ {
    ResponseEntity<ResponseData<PrMemberDTO>> addMember(PrMemberAddDTO dto);
}
