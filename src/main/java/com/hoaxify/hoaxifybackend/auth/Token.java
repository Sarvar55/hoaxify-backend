package com.hoaxify.hoaxifybackend.auth;

import com.hoaxify.hoaxifybackend.user.User;
import lombok.Data;

import javax.persistence.*;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Entity
@Data
public class Token {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long tokenId;
    private String token;

    @ManyToOne
    private User user;
}
