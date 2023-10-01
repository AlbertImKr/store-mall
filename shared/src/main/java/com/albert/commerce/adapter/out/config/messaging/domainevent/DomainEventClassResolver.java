package com.albert.commerce.adapter.out.config.messaging.domainevent;

import com.albert.commerce.domain.event.DomainEvent;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DomainEventClassResolver {

    private final Map<String, Class<? extends DomainEvent>> classMap;

    public DomainEventClassResolver(Set<Class<? extends DomainEvent>> classSet) {
        this.classMap = classSet.stream()
                .collect(Collectors.toMap(Class::getSimpleName, Function.identity()));
    }

    public boolean contains(String domainEventName) {
        return classMap.containsKey(domainEventName);
    }

    public Set<String> getClassNames() {
        return classMap.keySet();
    }
}
