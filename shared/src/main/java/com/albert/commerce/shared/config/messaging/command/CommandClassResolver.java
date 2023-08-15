package com.albert.commerce.shared.config.messaging.command;

import com.albert.commerce.shared.messaging.application.Command;
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
