package com.hoaxify.hoaxifybackend.auth;

import com.hoaxify.hoaxifybackend.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Repository
public interface TokenRepository extends JpaRepository<Token, String> {
    @Query("select t from Token t where t.token = ?1")
    Token findByToken(String token);
}
