package com.hoaxify.hoaxifybackend.Shared;

import com.hoaxify.hoaxifybackend.Shared.UniqueUsername;
import com.hoaxify.hoaxifybackend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private UserRepository userRepository;

    @Autowired
    public UniqueUsernameValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        return username != null && !userRepository.findByUserName(username).isPresent();
    }
}
