package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.Group;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GrRepo extends BaseRepo<Group> {
    List<Group> findAllByDeletedIsFalseAndActiveIsTrue();

//    List<Group> findAllByOwnerId(Long id);
    List<Group> findAllByOwnerUuid(UUID uuid);
    Optional<Group> findByOwnerUuid(UUID uuid);

    Optional<Group> findByUuid(UUID uuid);

    Group findByOwnerId(Long ownerId);

    Group findByUsername(String username);
}
