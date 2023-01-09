package uzum.trelloapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uzum.trelloapp.dto.task.TaskCrDTO;
import uzum.trelloapp.dto.task.TaskDTO;
import uzum.trelloapp.dto.task.TaskUpDTO;
import uzum.trelloapp.entity.Task;
import uzum.trelloapp.mapper.qualifier.GTypeQualifier;
import uzum.trelloapp.mapper.qualifier.PTypeQualifier;

@Mapper(componentModel = "spring",
        uses = {
                UserMap.class,
                GrMap.class,
                PrMap.class,
                PTypeQualifier.class,
                GTypeQualifier.class
        })
public interface TaskMap {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "link", source = "link")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "project", source = "project")
    @Mapping(target = "column", source = "column")
    @Mapping(target = "position", source = "position")
    TaskDTO toDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "projectId", source = "projectId")
    @Mapping(target = "columnId", source = "columnId")
    @Mapping(target = "position", source = "position")
    Task toEntity(TaskCrDTO dto);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "description", source = "dto.description")
    Task toEntity(@MappingTarget Task task, TaskUpDTO dto);

}
