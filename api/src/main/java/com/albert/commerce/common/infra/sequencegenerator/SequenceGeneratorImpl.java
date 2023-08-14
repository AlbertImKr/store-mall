package com.albert.commerce.common.infra.sequencegenerator;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import org.springframework.stereotype.Component;

@Component
public class SequenceGeneratorImpl implements SequenceGenerator {

    @Override
    public String generate() {
        return new ObjectId().toHexString();
    }
}
