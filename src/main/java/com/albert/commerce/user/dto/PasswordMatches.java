package com.albert.commerce.user.dto;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
public @interface PasswordMatches {

  String message() default "비밀 번호가 일치하지 않습니다.";

  Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
