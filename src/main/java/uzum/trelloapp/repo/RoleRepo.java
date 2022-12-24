package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.Role;

@Repository
public interface RoleRepo extends BaseRepo<Role> {

    Role findByName(String name);

}
