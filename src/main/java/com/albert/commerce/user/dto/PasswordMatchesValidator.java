package com.albert.commerce.user.dto;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, JoinRequest> {

    @Override
    public boolean isValid(JoinRequest joinRequest, ConstraintValidatorContext context) {
        return joinRequest.isSamePassword();
    }
}
