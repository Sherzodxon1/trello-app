package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskRepo extends BaseRepo<Task> {

    Optional<Task> findByUuid(UUID uuid);

    List<Task> findAllByOwnerUuid(UUID uuid);

    Task findByUsername(String username);
}
