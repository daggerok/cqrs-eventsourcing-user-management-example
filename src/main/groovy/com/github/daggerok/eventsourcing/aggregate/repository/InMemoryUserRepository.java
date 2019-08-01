package com.github.daggerok.eventsourcing.aggregate.repository;

import com.github.daggerok.eventsourcing.aggregate.User;
import com.github.daggerok.eventsourcing.aggregate.event.DomainEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, Collection<DomainEvent>> eventStore = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        Collection<DomainEvent> domainEvents = eventStore.getOrDefault(user.getUserId(), new ArrayList<>());
        Collection<DomainEvent> newEvents = Stream.concat(domainEvents.stream(), user.getEventStream().stream())
                                                  .collect(Collectors.toList());
        eventStore.put(user.getUserId(), newEvents);
        user.flushEvents();
    }

    @Override
    public User find(UUID userId) {
        return eventStore.containsKey(userId)
                ? User.recreate(userId, eventStore.get(userId))
                : null;
    }
}
