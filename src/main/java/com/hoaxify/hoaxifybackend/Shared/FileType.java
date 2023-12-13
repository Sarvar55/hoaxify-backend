package com.hoaxify.hoaxifybackend.Shared;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileTypeValidator.class})
public @interface FileType {

    String message() default "{hoax.validation.constraints.FileType.message}";//bunlar Java Bean spesfikasyonunu istedgi gerekli budug seyler
    //eger custom annatation olusturuyorsak bunlar olmasi lazim

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String []types();
}
