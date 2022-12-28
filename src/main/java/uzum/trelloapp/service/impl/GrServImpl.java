package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.gr.*;
import uzum.trelloapp.entity.Group;
import uzum.trelloapp.entity.GroupMembers;
import uzum.trelloapp.entity.Role;
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
        List<Group> list = repo.findAllByOwnerUuid(session.getUser().getUuid());
        if (Utils.isEmpty(list)) {
            log.warn("Guruhlar ro'yxati topilmadi!");
            return ResponseData.notFoundData("Group are not found !!!");
        }
        List<GrDTO> dtoList = new ArrayList<>();
        list.forEach(group -> dtoList.add(mapper.toDto(group)));
        log.info("Barcha guruhlar ro'yxati olindi");
        return ResponseData.success200(dtoList);
    }

    @Override
    public ResponseEntity<ResponseData<GrDTO>> get(GrGetDTO dto) {
        User user = userServ.checkUser(dto.getUser());  // check and get user
        Group group = this.checkGroup(dto.getGroup()); // check and get group
        for (Role role : user.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                Optional<Group> optional = repo.findByUuid(group.getUuid());
                if (Utils.isEmpty(optional)) {
                    log.error("Group uuid, {} bo'yicha ma'lumot topilmadi", group.getUuid());
                    ResponseData.notFoundData("Group not found !!!");
                }
                log.info("Group uuid, {} bo'yicha ma'lumot olindi", group.getUuid());
                return ResponseData.success200(mapper.toDto(optional.get()));

            } else {
                boolean isMember = this.isMember(group.getUuid(), user.getUuid());
                if (isMember) {
                    Optional<Group> optional = repo.findByUuid(group.getUuid());
                    if (Utils.isEmpty(optional)) {
                        log.error("Group uuid, {} bo'yicha ma'lumot topilmadi", group.getUuid());
                        ResponseData.notFoundData("Group not found !!!");
                    }
                    log.info("Group uuid, {} bo'yicha ma'lumot olindi", group.getUuid());
                    return ResponseData.success200(mapper.toDto(optional.get()));
                }
                log.warn("Siz bu guruhga a'zo emassiz !!!");
                return ResponseData.notFoundData("You are not member of this group !!!");
            }
        }
        return ResponseData.notFoundData("User role not defined !!!");
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

        String link = "https://trello.com/" + group.getName().toLowerCase() + "/" + UUID.randomUUID();
        String username = group.getName().toLowerCase() + System.currentTimeMillis();

        group.setUsername(username.replaceAll("\\s", ""));
        group.setLink(link.replaceAll("\\s", ""));
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

    @Transactional
    @Override
    public ResponseEntity<ResponseData<GrDTO>> edit(GrUpDTO dto) {
        Optional<Group> optional = repo.findByUuid(dto.getUuid());
        if (Utils.isEmpty(optional)) {
            log.error("Group uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("Group not found !!!");
        }
        Group group = optional.get();
        if (!group.isActive()) {
            log.error("Group uuid, {} bo'yicha faol emas!", dto.getUuid());
            return ResponseData.inActive("This group is not active !!!");
        } else if (!group.getOwnerId().equals(dto.getOwnerId())) {
            log.error("Sizda administrator huquqlari yo'q!");
            return ResponseData.inActive("You do not have admin rights !");
        }
        group = mapper.toEntity(group, dto);
        repo.save(group);
        log.info("Group {} - ma'lumotlari yangilandi!", group.getUsername());
        return ResponseData.success202(mapper.toDto(group));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<GrDTO>> delete(GrDelDTO dto) {

        Optional<Group> optional = repo.findByUuid(dto.getUuid());
        if (Utils.isEmpty(optional)) {
            log.error("Group uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            ResponseData.notFoundData("Group not found !!!");
        }

        Group group = optional.get();
        if (group.isDeleted()) {
            log.error("Group uuid, {} bo'yicha oldin o'chirilgan", dto.getUuid());
            return ResponseData.isDeleted("This group was previously disabled !!!");
        } else if (!group.getUsername().equals(dto.getUsername())) {
            log.error("Group username, {} bo'yicha username mos emas!", dto.getUsername());
            return ResponseData.errorStatus("Username is incorrect", HttpStatus.NOT_FOUND);
        }
        group.setDeleted(true);
        group.setActive(false);
        group.setUsername(group.getUsername() + "_isDel");
        repo.save(group);
        log.info("Group uuid, {} bo'yicha o'chirildi!", dto.getUuid());
        return ResponseData.success200(mapper.toDto(group));
    }

    @Override
    public Group checkGroup(UUID uuid) {
        Optional<Group> groupOptional = repo.findByUuid(uuid);
        if (groupOptional.isEmpty()) {
            log.error("Group uuid, {} bo'yicha ma'lumot topilmadi", uuid);
            throw new RuntimeException("Group not found!!!");
        }
        Group group = groupOptional.get();
        if (!group.isActive()) {
            log.error("Group uuid, {} bo'yicha faol emas!", uuid);
            throw new RuntimeException("This group is not active!!!");
        }
        return group;
    }

    @Override
    public boolean isMember(UUID groupUuid, UUID userUuid) {
        Optional<GroupMembers> groupMemberOptional = grMemberRepo.findByGroupUuidAndUserUuid(groupUuid, userUuid);
        return groupMemberOptional.isPresent();
    }
}
