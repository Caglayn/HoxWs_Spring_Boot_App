package com.caglayan.hoxws.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User createUser(User user){
        user.setPassword(this.encryptPassword(user.getPassword()));
        return userRepository.save(user);
    }

    private String encryptPassword(String password){
        return this.passwordEncoder.encode(password);
    }
}
