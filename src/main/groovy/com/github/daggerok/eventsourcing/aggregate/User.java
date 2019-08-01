package com.github.daggerok.eventsourcing.aggregate;

import com.github.daggerok.eventsourcing.aggregate.event.DomainEvent;
import com.github.daggerok.eventsourcing.aggregate.event.UserActivated;
import com.github.daggerok.eventsourcing.aggregate.event.UserCreated;
import com.github.daggerok.eventsourcing.aggregate.event.UserDeactivated;
import io.vavr.API;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.Predicates.instanceOf;

/**
 * Created user can be:
 * - activated
 * - deactivated
 * <p>
 * Activated user can be:
 * - deactivated
 * Activated user cannot be:
 * - activated
 * <p>
 * Deactivated user can be:
 * - activated
 * Deactivated user cannot be:
 * - deactivated
 */
@Getter
@ToString
@NoArgsConstructor
public class User implements Function<DomainEvent, User> {

    private UUID userId;
    private UserStatus state;

    private final Collection<DomainEvent> eventStream = new CopyOnWriteArrayList<>();

    public void flushEvents() {
        eventStream.clear();
    }

    public User(UUID userId) {
        create(userId);
    }

    public void create(UUID userId) {
        onCreate(new UserCreated(userId));
    }

    // cmd 0: create
    public void create() {
        create(UUID.randomUUID());
    }

    // evt: 0
    private User onCreate(UserCreated event) {
        eventStream.add(event);
        this.userId = event.getAggregateId();
        state = UserStatus.PENDING;
        return this;
    }

    // cmd 1: activate
    public void activate() {
        if (state == UserStatus.ACTIVE)
            throw new IllegalStateException("user is already active");
        onActivate(new UserActivated(userId));
    }

    // evt: 1
    private User onActivate(UserActivated event) {
        eventStream.add(event);
        state = UserStatus.ACTIVE;
        return this;
    }

    // cmd 2: deactivate
    public void deactivate() {
        if (state == UserStatus.SUSPENDED)
            throw new IllegalStateException("user is already suspended");
        onDeactivate(new UserDeactivated(userId));
    }

    // evt: 2
    private User onDeactivate(UserDeactivated event) {
        eventStream.add(event);
        state = UserStatus.SUSPENDED;
        return this;
    }

    /* es */

    public static User recreate(UUID userId, Collection<DomainEvent> domainEvents) {
        User snapshot = new User(userId);
        return io.vavr.collection.List.ofAll(domainEvents)
                                      .foldLeft(snapshot, User::apply);
    }

    @Override
    public User apply(DomainEvent domainEvent) {
        return API.Match(domainEvent).of(
                Case($(instanceOf(UserCreated.class)), this::onCreate),
                Case($(instanceOf(UserActivated.class)), this::onActivate),
                Case($(instanceOf(UserDeactivated.class)), this::onDeactivate)
        );
    }
}
