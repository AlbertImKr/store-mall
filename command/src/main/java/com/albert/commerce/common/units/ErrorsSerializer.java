package com.albert.commerce.common.units;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {


    @Override
    public void serialize(Errors errors, JsonGenerator gen, SerializerProvider serializers)
            throws IOException {
        errors.getFieldErrors().forEach(
                fieldError -> {
                    try {
                        gen.writeStartObject();
                        gen.writeStringField("field", fieldError.getField());
                        gen.writeStringField("objectName", fieldError.getObjectName());
                        gen.writeStringField("defaultMessage", fieldError.getDefaultMessage());
                        gen.writeStringField("code", fieldError.getCode());
                        Object rejectedValue = fieldError.getRejectedValue();
                        if (rejectedValue != null) {
                            gen.writeStringField("rejectedValue", rejectedValue.toString());
                        } else {
                            gen.writeStringField("rejectedValue", "");
                        }
                        gen.writeEndObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        errors.getGlobalErrors().forEach(
                error -> {
                    try {
                        gen.writeStartObject();
                        gen.writeStringField("objectName", error.getObjectName());
                        gen.writeStringField("code", error.getCode());
                        gen.writeStringField("defaultMessage", error.getDefaultMessage());
                        gen.writeEndObject();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
        gen.writeEndArray();
    }
}
