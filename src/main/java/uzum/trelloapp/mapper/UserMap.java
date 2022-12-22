package uzum.trelloapp.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import uzum.trelloapp.dto.auth.ReqRegDTO;
import uzum.trelloapp.dto.user.UserChPDTO;
import uzum.trelloapp.dto.user.UserCrDTO;
import uzum.trelloapp.dto.user.UserDTO;
import uzum.trelloapp.dto.user.UserUpDTO;
import uzum.trelloapp.entity.User;

@Mapper(componentModel = "spring")
public interface UserMap {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "active", source = "active")
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "deleted", source = "deleted")
    @Mapping(target = "superAdmin", source = "superAdmin")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "updatedAt", source = "updatedAt")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "password", source = "password")
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "superAdmin", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "password", source = "password")
    User toEntity(UserCrDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "superAdmin", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "password", source = "password")
    User toEntity(ReqRegDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "phone", source = "phone")
    User toEntity(@MappingTarget User user, UserUpDTO dto);

    @Mapping(target = "password", source = "dto.newPassword")
    User toEntity(@MappingTarget User user, UserChPDTO dto);
}
