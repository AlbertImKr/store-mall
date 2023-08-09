package com.albert.commerce.api.common.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
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

}
