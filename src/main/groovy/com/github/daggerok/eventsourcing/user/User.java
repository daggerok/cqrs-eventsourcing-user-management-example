package com.github.daggerok.eventsourcing.user;

import com.github.daggerok.eventsourcing.user.event.DomainEvent;
import com.github.daggerok.eventsourcing.user.event.UserActivated;
import com.github.daggerok.eventsourcing.user.event.UserDeactivated;
import io.vavr.API;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
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
 *
 * Activated user can be:
 * - deactivated
 * Activated user cannot be:
 * - activated
 *
 * Deactivated user can be:
 * - activated
 * Deactivated user cannot be:
 * - deactivated
 */
@Getter
@ToString
public class User implements Function<DomainEvent, User> {

    private final Collection<DomainEvent> eventStream = new CopyOnWriteArrayList<>();
    private final UUID userId;
    private UserStatus state;

    public User(UUID userId) {
        this.userId = userId;
        state = UserStatus.PENDING;
    }

    public void flushEvents() {
        eventStream.clear();
    }

    // cmd 1:
    public void activate() {
        if (state == UserStatus.ACTIVE)
            throw new IllegalStateException("user is already active");
        onActivate(new UserActivated(userId, ZonedDateTime.now()));
    }
    // evt: 1
    private User onActivate(UserActivated event) {
        eventStream.add(event);
        state = UserStatus.ACTIVE;
        return this;
    }

    // cmd 2:
    public void deactivate() {
        if (state == UserStatus.SUSPENDED)
            throw new IllegalStateException("user is already suspended");
        onDeactivate(new UserDeactivated(userId, ZonedDateTime.now()));
    }
    // evt 2:
    private User onDeactivate(UserDeactivated event) {
        eventStream.add(event);
        state = UserStatus.SUSPENDED;
        return this;
    }

    /* es */

    public static User recreate(User snapshot, Collection<DomainEvent> domainEvents) {
        return io.vavr.collection.List.ofAll(domainEvents)
                .foldLeft(snapshot, User::apply);
    }

    @Override
    public User apply(DomainEvent domainEvent) {
        return API.Match(domainEvent).of(
                Case($(instanceOf(UserActivated.class)), this::onActivate),
                Case($(instanceOf(UserDeactivated.class)), this::onDeactivate)
        );
    }
}
