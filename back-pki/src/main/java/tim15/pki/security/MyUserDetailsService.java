package tim15.pki.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if(username.equals("admin")) {
            ArrayList roles = new ArrayList<String>();
            roles.add("ROLE_ADMIN");
            roles.add("ROLE_USER");
            return new User("admin", "admin", roles);
        } else if(username.equals("user")) {
            ArrayList roles = new ArrayList<String>();
            roles.add("ROLE_USER");
            return new User("user", "user", roles);
        } else {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }
    }
}
