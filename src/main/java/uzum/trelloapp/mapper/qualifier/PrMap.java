package uzum.trelloapp.mapper.qualifier;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uzum.trelloapp.dto.pr.PrCrDTO;
import uzum.trelloapp.dto.pr.PrDTO;
import uzum.trelloapp.dto.pr.PrUpDTO;
import uzum.trelloapp.entity.Project;
import uzum.trelloapp.mapper.GrMap;
import uzum.trelloapp.mapper.UserMap;

@Mapper(componentModel = "spring",
        uses = {
                UserMap.class,
                GrMap.class,
                PTypeQualifier.class
        })
public interface PrMap {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "group", source = "group")
    @Mapping(target = "type", source = "type")
    PrDTO toDto(Project project);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "groupId", source = "groupId")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapProjectType")
    Project toEntity(PrCrDTO dto);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "type", source = "dto.type", qualifiedByName = "mapProjectType")
    Project toEntity(@MappingTarget Project project, PrUpDTO dto);
}
