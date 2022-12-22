package uzum.trelloapp.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uzum.trelloapp.entity.User;
import uzum.trelloapp.service.UserServ;

@Component
@RequiredArgsConstructor
public class UserSession {

    public final UserServ serv;

    public User getUser() {
        String phone = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return serv.findByPhone(phone);
    }
}
