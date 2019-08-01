package com.github.daggerok.eventsourcing.aggregate.event;

import java.time.ZonedDateTime;
import java.util.UUID;

public interface DomainEvent {
    UUID getAggregateId();

    ZonedDateTime getAt();
}
