package com.hoaxify.hoaxifybackend.auth;

import com.hoaxify.hoaxifybackend.error.AuthException;
import com.hoaxify.hoaxifybackend.user.User;
import com.hoaxify.hoaxifybackend.user.UserRepository;
import com.hoaxify.hoaxifybackend.user.UserService;
import com.hoaxify.hoaxifybackend.user.vm.UserVm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
@Slf4j
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository, TokenRepository tokenRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public AuthResponse authenticate(Credentials credentials) {
        User inDB = userRepository.findByUserName(credentials.getUserName()).get();
        if (inDB == null) {
            throw new AuthException();
        }
        boolean matches = passwordEncoder.matches(credentials.getPassword(), inDB.getPassword());
        if (!matches) {
            throw new AuthException();
        }
        UserVm userVM = new UserVm(inDB);
        String token = generateRandomToken();

        Token tokenEntity = new Token();
        tokenEntity.setToken(token);
        tokenEntity.setUser(inDB);
        tokenRepository.save(tokenEntity);
        AuthResponse response = new AuthResponse();
        response.setUser(userVM);
        response.setToken(token);
        return response;

    }

    @Transactional
    public UserDetails getUserDetails(String token) {
        Optional<Token> optionalToken = tokenRepository.findById(token);
        return optionalToken.isPresent() ? optionalToken.get().getUser() : null;
    }


    public String generateRandomToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
