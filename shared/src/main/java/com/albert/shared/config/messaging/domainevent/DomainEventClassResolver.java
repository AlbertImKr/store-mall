package com.albert.shared.config.messaging.domainevent;

import com.albert.shared.messaging.domain.event.DomainEvent;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainEventClassResolver {

    private final Map<String, Class<? extends DomainEvent>> classMap;

    public DomainEventClassResolver(Set<Class<? extends DomainEvent>> classMap) {
        this.classMap = classMap.stream()
                .collect(Collectors.toMap(Class::getName, Function.identity()));
    }

    public boolean contains(String domainEventName) {
        return classMap.containsKey(domainEventName);
    }

    public Set<String> getClassNames() {
        return classMap.keySet();
    }
}
