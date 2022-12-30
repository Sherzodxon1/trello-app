package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PrRepo extends BaseRepo<Project> {
    List<Project> findAllByOwnerUuid(UUID uuid);

    Optional<Project> findByUuid(UUID uuid);

    Project findByUsername(String username);
}
