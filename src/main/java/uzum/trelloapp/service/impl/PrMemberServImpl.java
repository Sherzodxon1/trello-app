package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.pr.PrMemberAddDTO;
import uzum.trelloapp.dto.pr.PrMemberDTO;
import uzum.trelloapp.entity.Project;
import uzum.trelloapp.entity.ProjectMembers;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.PrMemberMap;
import uzum.trelloapp.repo.PrMemberRepo;
import uzum.trelloapp.service.PrServ;
import uzum.trelloapp.service.UserServ;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrMemberServImpl implements PrMemberServ {

    private final UserServ userServ;
    private final PrServ prServ;
    private final PrMemberRepo prMemberRepo;
    private final PrMemberMap mapper;

    @Transactional
    @Override
    public ResponseEntity<ResponseData<PrMemberDTO>> addMember(PrMemberAddDTO dto) {
        User host = userServ.checkUser(dto.getHost());  // check and get user
        User guest = userServ.checkUser(dto.getGuest());  // check and get user
        Project project = prServ.checkProject(dto.getProject()); // check and get project
        for (Role role : host.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                ProjectMembers member = new ProjectMembers();
                member.setUserId(guest.getId());
                member.setProjectId(project.getId());
                prMemberRepo.save(member);
                log.info("Yangi project member id -> {} bo'yicha - project id -> {} ga biriktirildi", member.getUserId(), member.getProjectId());
                return ResponseData.success201(mapper.toDto(member));
            } else if (Utils.isOwner(project.getOwnerId(), host.getId())) {
                ProjectMembers member = new ProjectMembers();
                member.setUserId(guest.getId());
                member.setProjectId(project.getId());
                prMemberRepo.save(member);
                log.info("Yangi project member id -> {} bo'yicha - project id -> {} ga biriktirildi", member.getUserId(), member.getProjectId());
                return ResponseData.success201(mapper.toDto(member));
            }
            log.warn("Sizda admin huquqlari yo'q !!!");
            return ResponseData.notFoundData("You do not have admin rights!");
        }
        return ResponseData.notFoundData("User role not defined !!!");
    }

}
