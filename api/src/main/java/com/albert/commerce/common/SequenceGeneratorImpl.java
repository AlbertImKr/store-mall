package com.albert.commerce.common;

import org.springframework.stereotype.Component;

@Component
public class SequenceGeneratorImpl implements SequenceGenerator {

    @Override
    public String generate() {
        return new ObjectId().toHexString();
    }
}
