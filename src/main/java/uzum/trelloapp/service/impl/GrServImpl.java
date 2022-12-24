package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.GrCrDTO;
import uzum.trelloapp.dto.gr.GrDTO;
import uzum.trelloapp.dto.gr.GrDelDTO;
import uzum.trelloapp.dto.gr.GrUpDTO;
import uzum.trelloapp.entity.Group;
import uzum.trelloapp.entity.GroupMembers;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.helper.UserSession;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.GrMap;
import uzum.trelloapp.repo.GrMemberRepo;
import uzum.trelloapp.repo.GrRepo;
import uzum.trelloapp.service.GrServ;
import uzum.trelloapp.service.UserServ;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class GrServImpl implements GrServ {

    private final GrRepo repo;
    private final GrMap mapper;
    private final GrMemberRepo grMemberRepo;
    private final UserServ userServ;
    private final UserSession session;

    @Override
    public ResponseEntity<ResponseData<List<GrDTO>>> getAll() {
        List<Group> list = repo.findAllByOwnerId(session.getUser().getId());
        if (list.isEmpty()) {
            log.warn("Guruhlar ro'yxati topilmadi!");
            return ResponseData.notFoundData("Group are not found !!!");
        }
        List<GrDTO> dtoList = new ArrayList<>();
        list.forEach(group -> dtoList.add(mapper.toDto(group)));
        log.info("Barcha guruhlar ro'yxati olindi");
        return ResponseData.success200(dtoList);
    }

    @Override
    public ResponseEntity<ResponseData<GrDTO>> get(UUID uuid) {
        Optional<Group> group = repo.findByUuid(uuid);
        if (Utils.isEmpty(group)) {
            log.error("Group uuid, {} bo'yicha ma'lumot topilmadi", uuid);
            ResponseData.notFoundData("Group not found !!!");
        }
        log.info("Group uuid, {} bo'yicha ma'lumot olindi", uuid);
        return ResponseData.success200(mapper.toDto(group.get()));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<GrDTO>> add(GrCrDTO dto) throws UserNotFoundException {
        Group group = mapper.toEntity(dto);

        User user = userServ.findByUuid(session.getUser().getUuid());
        if (Utils.isEmpty(user)) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", session.getUser().getUuid());
            return ResponseData.notFoundData("User is not found !!!");
        } else if (!user.isActive()) {
            log.error("User uuid, {} bo'yicha faol emas!", session.getUser().getUuid());
            return ResponseData.inActive("This User's status is inactive !!!");
        }

        group.setUsername("username" + System.currentTimeMillis());
        repo.save(group);
        log.info("Yangi group - {} saqlandi", group.getName());

        Group memberGroup = repo.findByUsername(group.getUsername());
        log.info("Group username, {} bo'yicha ma'lumot olindi", group.getUsername());

        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setGroupId(memberGroup.getId());
        groupMembers.setUserId(group.getOwnerId());

        grMemberRepo.save(groupMembers);
        log.info("Yangi group member id -> {} bo'yicha - group id -> {} ga biriktirildi", groupMembers.getUserId(), groupMembers.getGroupId());
        return ResponseData.success201(mapper.toDto(group));
    }

    @Override
    public ResponseEntity<ResponseData<GrDTO>> edit(GrUpDTO dto) {
        return null;
    }

    @Override
    public ResponseEntity<ResponseData<GrDTO>> delete(GrDelDTO dto) {
        return null;
    }
}
