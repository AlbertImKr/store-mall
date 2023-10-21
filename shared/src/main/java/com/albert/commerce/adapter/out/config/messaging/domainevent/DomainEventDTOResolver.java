package com.albert.commerce.adapter.out.config.messaging.domainevent;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainEventDTOResolver {

    private final Map<String, Class<?>> classMap;

    public DomainEventDTOResolver(Set<Class<?>> classSet) {
        this.classMap = classSet.stream()
                .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
    }

    public boolean contains(String domainEventName) {
        return classMap.containsKey(domainEventName);
    }

    public String[] getChannelNames() {
        return classMap.keySet()
                .toArray(new String[0]);
    }

    public Class<?> get(String channelName) {
        return classMap.get(channelName);
    }
}
