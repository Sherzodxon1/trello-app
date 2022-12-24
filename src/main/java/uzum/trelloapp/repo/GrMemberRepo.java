package uzum.trelloapp.repo;

import org.springframework.stereotype.Repository;
import uzum.trelloapp.base.BaseRepo;
import uzum.trelloapp.entity.GroupMembers;

import java.util.Optional;

@Repository
public interface GrMemberRepo extends BaseRepo<GroupMembers> {

    Optional<GroupMembers> findByGroupIdAndUserId(Long groupId, Long userId);

}
