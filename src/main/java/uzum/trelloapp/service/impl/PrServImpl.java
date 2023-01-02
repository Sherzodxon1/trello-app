package uzum.trelloapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uzum.trelloapp.common.ResponseData;
import uzum.trelloapp.dto.pr.*;
import uzum.trelloapp.entity.*;
import uzum.trelloapp.enums.PrColType;
import uzum.trelloapp.exception.UserNotFoundException;
import uzum.trelloapp.helper.UserSession;
import uzum.trelloapp.helper.Utils;
import uzum.trelloapp.mapper.qualifier.PrMap;
import uzum.trelloapp.repo.PrColRepo;
import uzum.trelloapp.repo.PrMemberRepo;
import uzum.trelloapp.repo.PrRepo;
import uzum.trelloapp.service.GrServ;
import uzum.trelloapp.service.PrServ;
import uzum.trelloapp.service.UserServ;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrServImpl implements PrServ {

    private final PrRepo repo;
    private final PrMap mapper;
    private final PrMemberRepo prMemberRepo;
    private final PrColRepo prColRepo;
    private final GrServ grServ;
    private final UserServ userServ;
    private final UserSession session;

    @Override
    public ResponseEntity<ResponseData<List<PrDTO>>> getAll() {
        List<Project> list = repo.findAllByOwnerUuid(session.getUser().getUuid());
        if (Utils.isEmpty(list)) {
            log.warn("Proyektlar ro'yxati topilmadi!");
            return ResponseData.notFoundData("Projects are not found !!!");
        }
        List<PrDTO> dtoList = new ArrayList<>();
        list.forEach(project -> dtoList.add(mapper.toDto(project)));
        log.info("Barcha proyektlar ro'yxati olindi");
        return ResponseData.success200(dtoList);
    }

    @Override
    public ResponseEntity<ResponseData<PrDTO>> get(PrGetDTO dto) {
        User user = userServ.checkUser(dto.getUser());
        Project project = this.checkProject(dto.getProject());
        for (Role role : user.getRoles()) {
            if (Utils.isAdmin(role.getName())) {
                Optional<Project> optional = repo.findByUuid(project.getUuid());
                if (Utils.isEmpty(optional)) {
                    log.error("Project uuid, {} bo'yicha ma'lumot topilmadi", project.getUuid());
                    return ResponseData.notFoundData("Project not found !!!");
                }
                log.info("Project uuid, {} bo'yicha ma'lumot olindi", project.getUuid());
                return ResponseData.success200(mapper.toDto(optional.get()));
            } else {
                boolean isMember = this.isMember(project.getUuid(), user.getUuid());
                if (!isMember) {
                    log.warn("Siz bu proyektga a'zo emassiz !!!");
                    return ResponseData.notFoundData("You are not member of this project !!!");
                } else {
                    Optional<Project> optional = repo.findByUuid(project.getUuid());
                    if (Utils.isEmpty(optional)) {
                        log.error("Project uuid, {} bo'yicha ma'lumot topilmadi", project.getUuid());
                        ResponseData.notFoundData("Project not found !!!");
                    }
                    log.info("Project uuid, {} bo'yicha ma'lumot olindi", project.getUuid());
                    return ResponseData.success200(mapper.toDto(optional.get()));
                }
            }
        }
        return ResponseData.notFoundData("User role not defined !!!");
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<PrDTO>> add(PrCrDTO dto) throws UserNotFoundException {
        User user = userServ.findByUuid(session.getUser().getUuid());
        if (Utils.isEmpty(user)) {
            log.error("User uuid, {} bo'yicha ma'lumot topilmadi", session.getUser().getUuid());
            return ResponseData.notFoundData("User not found !!!");
        } else if (!user.isActive()) {
            log.error("User uuid, {} bo'yicha faol emas!", session.getUser().getUuid());
            return ResponseData.inActive("This User's status is inactive !!!");
        }

        Group group = grServ.checkGroup(dto.getGroupId());
        Project project = mapper.toEntity(dto);

        String link = "https://trello.com/" + "b/" + UUID.randomUUID();
        String username = project.getName().toLowerCase() + System.currentTimeMillis();
        project.setGroupId(group.getId());
        project.setUsername(username.replaceAll("\\s", ""));
        project.setLink(link.replaceAll("\\s", ""));
        repo.save(project);
        log.info("Yangi project - {} saqlandi", project.getName());

        Project memberProject = repo.findByUsername(project.getUsername());
        log.info("Project username, {} bo'yicha ma'lumot olindi", project.getUsername());

        ProjectMembers projectMembers = new ProjectMembers();
        projectMembers.setProjectId(memberProject.getId());
        projectMembers.setUserId(project.getOwnerId());
        log.info("Yangi project member id -> {} bo'yicha - project id -> {} ga biriktirildi", projectMembers.getUserId(), projectMembers.getProjectId());
        prMemberRepo.save(projectMembers);

        ProjectColumn todo = new ProjectColumn();
        todo.setName(PrColType.TODO.name());
        todo.setProjectId(project.getId());
        todo.setPosition(PrColType.TODO.getPosition());
        prColRepo.save(todo);
        log.info("Yangi project column name -> {} qo'shildi", todo.getName());

        ProjectColumn doing = new ProjectColumn();
        doing.setName(PrColType.DOING.name());
        doing.setProjectId(project.getId());
        doing.setPosition(PrColType.DOING.getPosition());
        prColRepo.save(doing);
        log.info("Yangi project column name -> {} qo'shildi", doing.getName());

        ProjectColumn done = new ProjectColumn();
        done.setName(PrColType.DONE.name());
        done.setProjectId(project.getId());
        done.setPosition(PrColType.DONE.getPosition());
        prColRepo.save(done);
        log.info("Yangi project column name -> {} qo'shildi", done.getName());

        ProjectColumn archive = new ProjectColumn();
        archive.setName(PrColType.ARCHIVE.name());
        archive.setProjectId(project.getId());
        archive.setPosition(PrColType.ARCHIVE.getPosition());
        prColRepo.save(archive);
        log.info("Yangi project column name -> {} qo'shildi", archive.getName());

        return ResponseData.success201(mapper.toDto(project));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<PrDTO>> edit(PrUpDTO dto) {
        Optional<Project> optional = repo.findByUuid(dto.getUuid());
        if (Utils.isEmpty(optional)) {
            log.error("Project uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            return ResponseData.notFoundData("Project not found !!!");
        }
        Project project = optional.get();
        if (!project.isActive()) {
            log.error("Project uuid, {} bo'yicha faol emas!", dto.getUuid());
            return ResponseData.inActive("This project is not active !!!");
        } else if (!Utils.isOwner(project.getOwnerId(), dto.getOwnerId())) {
            log.error("Sizda administrator huquqlari yo'q!");
            return ResponseData.inActive("You do not have admin rights !");
        }
        project = mapper.toEntity(project, dto);
        repo.save(project);
        log.info("Project {} - ma'lumotlari yangilandi!", project.getUsername());
        return ResponseData.success202(mapper.toDto(project));
    }

    @Transactional
    @Override
    public ResponseEntity<ResponseData<PrDTO>> delete(PrDelDTO dto) {
        Optional<Project> optional = repo.findByUuid(dto.getUuid());
        if (Utils.isEmpty(optional)) {
            log.error("Project uuid, {} bo'yicha ma'lumot topilmadi", dto.getUuid());
            ResponseData.notFoundData("Project not found !!!");
        }

        Project project = optional.get();
        if (project.isDeleted()) {
            log.error("Group uuid, {} bo'yicha oldin o'chirilgan", dto.getUuid());
            return ResponseData.isDeleted("This group was previously disabled !!!");
        } else if (!project.getUsername().equals(dto.getUsername())) {
            log.error("Group username, {} bo'yicha username mos emas!", dto.getUsername());
            return ResponseData.errorStatus("Username is incorrect", HttpStatus.NOT_FOUND);
        }

        project.setDeleted(true);
        project.setActive(false);
        project.setUsername(project.getUsername() + "_isDel");
        repo.save(project);
        log.info("Project uuid, {} bo'yicha o'chirildi!", dto.getUuid());
        return ResponseData.success200(mapper.toDto(project));
    }

    @Override
    public Project checkProject(UUID uuid) {
        Optional<Project> optional = repo.findByUuid(uuid);
        if (optional.isEmpty()) {
            log.error("Project uuid, {} bo'yicha ma'lumot topilmadi", uuid);
            throw new RuntimeException("Project not found!!!");
        }
        Project project = optional.get();
        if (!project.isActive()) {
            log.error("Project uuid, {} bo'yicha faol emas!", uuid);
            throw new RuntimeException("This project is not active!!!");
        }
        return project;
    }

    @Override
    public boolean isMember(UUID project, UUID user) {
        Optional<ProjectMembers> prMembers = prMemberRepo.findByProjectUuidAndUserUuid(project, user);
        return prMembers.isPresent();
    }
}
