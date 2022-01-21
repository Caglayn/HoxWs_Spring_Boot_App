package com.caglayan.hoxws.auth;

import com.caglayan.hoxws.configuration.HoaxUserDetails;
import com.caglayan.hoxws.user.User;
import com.caglayan.hoxws.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserAuthService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User inDB = userService.findByUsername(username);
        if (inDB == null)
            throw new UsernameNotFoundException("User not found");
        return new HoaxUserDetails(inDB);
    }
}
