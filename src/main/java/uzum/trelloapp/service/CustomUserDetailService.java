package uzum.trelloapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uzum.trelloapp.entity.Role;
import uzum.trelloapp.entity.User;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserServ userServ;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        try {
            User user = userServ.findByPhone(phone);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            for (Role role : user.getRoles()) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
            }
            return new org.springframework.security.core.userdetails.User(
                    phone,
                    user.getPassword(),
                    authorities
            );
        } catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("This phone is not registered!!!");
        }
    }

}
