package com.albert.commerce.shared.config.messaging.domainevent;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainEventClassResolver {

    private final Map<String, Class<? extends DomainEvent>> classMap;

    public DomainEventClassResolver(Set<Class<? extends DomainEvent>> classSet) {
        this.classMap = classSet.stream()
                .collect(Collectors.toMap(Class::getName, Function.identity()));
    }

    public boolean contains(String domainEventName) {
        return classMap.containsKey(domainEventName);
    }

    public Set<String> getClassNames() {
        return classMap.keySet();
    }
}
