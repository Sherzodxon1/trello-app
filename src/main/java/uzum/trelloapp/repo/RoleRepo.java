package uzum.trelloapp.repo;

import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.Role;

public interface RoleRepo extends BaseRepo<Role> {

    Role findByName(String name);

}
