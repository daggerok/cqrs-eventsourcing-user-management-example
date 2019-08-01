package com.github.daggerok.eventsourcing.aggregate.repository;

import com.github.daggerok.eventsourcing.aggregate.User;
import com.github.daggerok.eventsourcing.aggregate.event.DomainEvent;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryUserRepository implements UserRepository {

    private final Map<UUID, Collection<DomainEvent>> eventStore = new ConcurrentHashMap<>();

    @Override
    public void save(User user) {
        UUID aggregateId = user.getUserId();
        Collection<DomainEvent> newEvents = user.getEventStream();
        Collection<DomainEvent> oldEvents = eventStore.getOrDefault(aggregateId, new CopyOnWriteArrayList<>());
        eventStore.put(aggregateId, Stream.concat(oldEvents.stream(), newEvents.stream())
                                          .collect(Collectors.toList()));
        user.flushEvents();
    }

    @Override
    public User find(UUID userId) {
        User snapshot = new User();
        return eventStore.containsKey(userId)
                ? User.recreate(snapshot, eventStore.get(userId))
                : snapshot;
    }
}
