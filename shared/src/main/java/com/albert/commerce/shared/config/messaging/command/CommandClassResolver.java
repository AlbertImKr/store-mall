package com.albert.commerce.shared.config.messaging.command;

import com.albert.commerce.shared.messaging.application.Command;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandClassResolver {

    private final Map<String, Class<? extends Command>> classMap;

    public CommandClassResolver(Set<Class<? extends Command>> classMap) {
        this.classMap = classMap.stream()
                .collect(Collectors.toMap(Class::getName, Function.identity()));
    }

    public Set<String> getNames() {
        return classMap.keySet();
    }

    public Object resolve(String commandClassname) {
        return classMap.get(commandClassname);
    }
}
