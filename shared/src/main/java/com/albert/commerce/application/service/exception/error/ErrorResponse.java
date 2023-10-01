package com.albert.commerce.application.service.exception.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import org.springframework.hateoas.RepresentationModel;

public class ErrorResponse extends RepresentationModel<ErrorResponse> {

    @JsonProperty(value = "error-message")
    private ErrorMessage errorMessage;

    public ErrorResponse() {
    }

    public ErrorResponse(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ErrorResponse that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return getErrorMessage() == that.getErrorMessage();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getErrorMessage());
    }
}
