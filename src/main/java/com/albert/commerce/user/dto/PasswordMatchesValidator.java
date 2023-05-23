package com.albert.commerce.user.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, JoinRequest> {

    @Override
    public boolean isValid(JoinRequest joinRequest, ConstraintValidatorContext context) {
        return joinRequest.isSamePassword();
    }
}
