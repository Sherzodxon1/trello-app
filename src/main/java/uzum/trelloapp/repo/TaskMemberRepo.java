package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.TaskMembers;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskMemberRepo extends BaseRepo<TaskMembers> {
    Optional<TaskMembers> findByTaskUuidAndUserUuid(UUID task, UUID user);
}
