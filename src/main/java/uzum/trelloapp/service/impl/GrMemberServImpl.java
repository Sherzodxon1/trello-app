package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.GrMemberAddDTO;
import uzum.trelloapp.dto.gr.GrMemberDTO;
import uzum.trelloapp.entity.Group;
import uzum.trelloapp.entity.GroupMembers;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.GrMemberMap;
import uzum.trelloapp.repo.GrMemberRepo;
import uzum.trelloapp.service.GrMemberServ;
import uzum.trelloapp.service.GrServ;
import uzum.trelloapp.service.UserServ;

import javax.transaction.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrMemberServImpl implements GrMemberServ {

    private final UserServ userServ;
    private final GrServ grServ;
    private final GrMemberRepo grMemberRepo;
    private final GrMemberMap mapper;

    @Transactional
    @Override
    public ResponseEntity<ResponseData<GrMemberDTO>> addMember(GrMemberAddDTO dto) {
        User host = userServ.checkUser(dto.getHost());  // check and get user
        User guest = userServ.checkUser(dto.getGuest());  // check and get user
        Group group = grServ.checkGroup(dto.getGroup()); // check and get group
        for (Role role : host.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                GroupMembers groupMembers = new GroupMembers();
                groupMembers.setUserId(guest.getId());
                groupMembers.setGroupId(group.getId());
                grMemberRepo.save(groupMembers);
                log.info("Yangi group member id -> {} bo'yicha - group id -> {} ga biriktirildi", groupMembers.getUserId(), groupMembers.getGroupId());
                return ResponseData.success201(mapper.toDto(groupMembers));
            } else if (Utils.isOwner(group.getOwnerId(), host.getId())) {
                GroupMembers groupMembers = new GroupMembers();
                groupMembers.setUserId(guest.getId());
                groupMembers.setGroupId(group.getId());
                grMemberRepo.save(groupMembers);
                log.info("Yangi group member id -> {} bo'yicha - group id -> {} ga biriktirildi", groupMembers.getUserId(), groupMembers.getGroupId());
                return ResponseData.success201(mapper.toDto(groupMembers));
            }
            log.warn("Sizda admin huquqlari yo'q !!!");
            return ResponseData.notFoundData("You do not have admin rights!");
        }
        return ResponseData.notFoundData("User role not defined !!!");
    }

}
