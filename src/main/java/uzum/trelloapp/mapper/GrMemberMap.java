package uzum.trelloapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uzum.trelloapp.dto.gr.GrCrDTO;
import uzum.trelloapp.dto.gr.GrDTO;
import uzum.trelloapp.dto.gr.GrMemberDTO;
import uzum.trelloapp.dto.gr.GrUpDTO;
import uzum.trelloapp.entity.Group;
import uzum.trelloapp.entity.GroupMembers;
import uzum.trelloapp.mapper.qualifier.GTypeQualifier;

@Mapper(componentModel = "spring",
        uses = {
                UserMap.class,
                GrMap.class,
                GTypeQualifier.class
        })
public interface GrMemberMap {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "group", source = "group")
    GrMemberDTO toDto(GroupMembers groupMembers);

    /*@Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "ownerId", source = "ownerId")
    @Mapping(target = "type", source = "type", qualifiedByName = "mapGroupType")
    Group toEntity(GrCrDTO dto);

    @Mapping(target = "name", source = "dto.name")
    @Mapping(target = "username", source = "dto.username")
    @Mapping(target = "description", source = "dto.description")
    @Mapping(target = "type", source = "dto.type", qualifiedByName = "mapGroupType")
    Group toEntity(@MappingTarget Group group, GrUpDTO dto);*/
}
