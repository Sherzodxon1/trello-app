package uzum.trelloapp.mapper.qualifier;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;
import uzum.trelloapp.enums.ProjectType;

@Component
@Mapper
public class PTypeQualifier {

    @Named("mapProjectType")
    public ProjectType mapProjectType(String type) {
        return ProjectType.getByName(type);
    }
}
