package com.albert.commerce.common.jpa;

import com.albert.commerce.common.model.Money;
import jakarta.persistence.AttributeConverter;

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
