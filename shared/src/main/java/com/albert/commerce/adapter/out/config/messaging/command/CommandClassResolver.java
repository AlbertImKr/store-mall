package com.albert.commerce.adapter.out.config.messaging.command;

import com.albert.commerce.application.service.Command;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandClassResolver {

    private final Map<String, Class<? extends Command>> classMap;

    public CommandClassResolver(Set<Class<? extends Command>> classSet) {
        this.classMap = classSet.stream()
                .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
    }

    public Set<String> getNames() {
        return classMap.keySet();
    }
}
