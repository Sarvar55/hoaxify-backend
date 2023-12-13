package com.hoaxify.hoaxifybackend.auth;

import com.fasterxml.jackson.annotation.JsonView;
import com.hoaxify.hoaxifybackend.Shared.Views;
import com.hoaxify.hoaxifybackend.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@RestController
@RequestMapping("/api/1.0/auth")
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthService authService, PasswordEncoder passwordEncoder) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping
    ResponseEntity<AuthResponse> handleAuthentication(@RequestBody Credentials credentials) {

        return ResponseEntity.ok(authService.authenticate(credentials));
        //required=false deriz ve bu olmasa da bizim methoda giricek ve biz ordan kendimiz korntroledicegiz
        //eger bize Authorization headeri icincde bir sey gelmesse biz ona cevap olarak 401 doonmeliyiz

//        if (authorization == null || authorization.isEmpty()) {
//            ApiError error = new ApiError(401, "Unauthorized request", "api/1.0/auth");
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
//        }
//        String auth = authorization.substring(6);
//        String decoded = new String(Base64.getDecoder().decode(auth));
//        String[] parts = decoded.split(":");
//        Optional<User> inDB = null;
//        String username = parts[0];
//        String password = parts[1];
//
//        inDB =
        // bunu boyle sebebi biz user odonucegmiz icin her defasinda o userin auth headerinden encode bilgiyi cekib
        // decode edib db ye sorgu atiyorduk indi ise bu sekilde yapicagiz principaldan alicagiz
        //CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        //User user = userRepository.findByUserName(userDetails.getUsername()).get();
    }
}
