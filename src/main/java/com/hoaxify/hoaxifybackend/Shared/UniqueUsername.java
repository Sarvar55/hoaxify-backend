package com.hoaxify.hoaxifybackend.Shared;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @project: hoaxify-backend
 * @author: Sarvar55
 */

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UniqueUsernameValidator.class })
public @interface UniqueUsername {
    String message() default "{hoax.validation.constraints.UniqueUsername.message}";//bunlar Java Bean spesfikasyonunu istedgi gerekli budug seyler
    //eger custom annatation olusturuyorsak bunlar olmasi lazim

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
