package com.github.daggerok.eventsourcing.aggregate.event;

import com.github.daggerok.eventsourcing.aggregate.UserStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
// @NoArgsConstructor(access = AccessLevel.PACKAGE)
public class UserDeactivated implements DomainEvent {
    @NonNull UUID aggregateId;
    final UserStatus state = UserStatus.SUSPENDED;
    final ZonedDateTime at = ZonedDateTime.now();
}
