package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepo extends BaseRepo<User> {
    List<User> findAllByDeletedIsFalseAndActiveIsTrue();

    Optional<User> findByUuid(UUID uuid);

    Optional<User> findByPhoneAndPassword(String phone, String password);

    Optional<User> findByPhone(String phone);

    Optional<User> findByUsername(String username);
}
