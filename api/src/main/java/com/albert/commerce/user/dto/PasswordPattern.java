package com.albert.commerce.user.dto;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$")
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface PasswordPattern {

    String message() default "비밀번호는 대소문자,특수문자 @#$%^&+=!,숫자가 적어도 하나가 있어야 하며 공백이 아닌 8~20자이여야 한다";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
