package com.github.daggerok.eventsourcing.user.event;

import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserActivated implements DomainEvent {
    UUID aggregateId;
    ZonedDateTime at;
}
