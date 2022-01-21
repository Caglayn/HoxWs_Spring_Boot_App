package com.caglayan.hoxws.user;

import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquUsernameValidator implements ConstraintValidator<UniqueUsername, String > {

    private final UserRepository userRepository;

    @Autowired
    public UniquUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        User user = userRepository.findByUsername(username);
        if (user != null)
            return false;

        return true;
    }
}
