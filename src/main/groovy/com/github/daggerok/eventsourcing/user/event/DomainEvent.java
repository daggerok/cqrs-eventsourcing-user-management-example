package com.github.daggerok.eventsourcing.user.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();
    ZonedDateTime getAt();
}
