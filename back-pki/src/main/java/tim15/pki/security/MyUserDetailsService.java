package tim15.pki.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")) {
            return new User("admin", "admin", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN, ROLE_USER"));
        } else if(username.equals("user")) {
            return new User("user", "user", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER"));
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
    }
}
