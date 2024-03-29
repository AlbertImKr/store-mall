package com.albert.commerce.adapter.out.persistence.converters;

import com.albert.commerce.adapter.out.persistence.Money;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class MoneyConverter implements AttributeConverter<Money, Long> {

    @Override
    public Long convertToDatabaseColumn(Money money) {
        return money == null ? null : money.value();
    }

    @Override
    public Money convertToEntityAttribute(Long value) {
        return value == null ? null : new Money(Math.toIntExact(value));
    }
}
