package uzum.trelloapp.mapper.qualifier;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import uzum.trelloapp.enums.GroupType;

@Component
@Mapper
public class GTypeQualifier {

    @Named("mapGroupType")
    public GroupType mapGroupType(String type) {
        return GroupType.getByName(type);
    }
}
