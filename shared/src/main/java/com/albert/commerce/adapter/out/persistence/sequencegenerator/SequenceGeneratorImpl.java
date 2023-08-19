package com.albert.commerce.adapter.out.persistence.sequencegenerator;

import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import org.springframework.stereotype.Component;

@Component
public class SequenceGeneratorImpl implements SequenceGenerator {

    @Override
    public String generate() {
        return new ObjectId().toHexString();
    }
}
