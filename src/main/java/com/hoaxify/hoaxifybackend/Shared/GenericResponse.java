package com.hoaxify.hoaxifybackend.Shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponse {
    private String message;
    private HttpStatus status;

    public GenericResponse(String message) {
        this.setMessage(message);
    }
}

