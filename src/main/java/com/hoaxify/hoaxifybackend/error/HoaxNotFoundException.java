package com.hoaxify.hoaxifybackend.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class HoaxNotFoundException extends RuntimeException {
}
