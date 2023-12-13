package com.hoaxify.hoaxifybackend.error;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Data
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{
    private String message;

    public NotFoundException(String message){
        this.message=message;
    }

}
