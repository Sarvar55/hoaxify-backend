package com.hoaxify.hoaxifybackend.auth;

import com.hoaxify.hoaxifybackend.user.vm.UserVm;
import lombok.Data;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
public class AuthResponse {
    private String token;
    private UserVm user;
}
