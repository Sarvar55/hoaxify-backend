package com.hoaxify.hoaxifybackend.Shared;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@AuthenticationPrincipal//bu da principalda bizim yerimize user alicak
public @interface CurrentUser {

}
