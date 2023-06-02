package com.albert.authorizationserver.dto;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, JoinRequest> {

    @Override
    public boolean isValid(JoinRequest joinRequest, ConstraintValidatorContext context) {
        return joinRequest.isSamePassword();
    }
}
