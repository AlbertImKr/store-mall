package com.albert.authorizationserver.dto;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

@Slf4j
@JsonComponent
public class ErrorSerializer extends JsonSerializer<Errors> {

    private static Consumer<ObjectError> getGlobalErrorConsumer(JsonGenerator gen) {
        return error -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("objectName", error.getObjectName());
                gen.writeStringField("code", error.getCode());
                gen.writeStringField("defaultMessage", error.getDefaultMessage());
                gen.writeEndObject();
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        };
    }

    private static Consumer<FieldError> getFieldErrorConsumer(JsonGenerator gen) {
        return error -> {
            try {
                gen.writeStartObject();
                gen.writeStringField("filed", error.getField());
                gen.writeStringField("objectName", error.getObjectName());
                gen.writeStringField("code", error.getCode());
                gen.writeStringField("defaultMessage", error.getDefaultMessage());
                Object rejectedValue = error.getRejectedValue();
                if (rejectedValue != null) {
                    gen.writeStringField("rejectedValue", rejectedValue.toString());
                }
                gen.writeEndObject();
            } catch (IOException e) {
                log.debug(e.getMessage());
            }
        };
    }

    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        gen.writeStartArray();
        errors.getFieldErrors().forEach(getFieldErrorConsumer(gen));
        errors.getGlobalErrors().forEach(getGlobalErrorConsumer(gen));
        gen.writeEndArray();
    }
}
