package com.hoaxify.hoaxifybackend.config;

import com.hoaxify.hoaxifybackend.user.User;
import com.hoaxify.hoaxifybackend.user.UserRepository;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Service
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user =userRepository.findByUserName(username);
        if(user.isPresent())
            return user.get();
        throw new UsernameNotFoundException("user not found");
    }
}
