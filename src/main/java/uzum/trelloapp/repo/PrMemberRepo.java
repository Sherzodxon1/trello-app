package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.GroupMembers;
import uzum.trelloapp.entity.ProjectMembers;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrMemberRepo extends BaseRepo<ProjectMembers> {

    Optional<ProjectMembers> findByProjectIdAndUserId(Long project, Long user);

    Optional<ProjectMembers> findByProjectUuidAndUserUuid(UUID project, UUID user);
}
