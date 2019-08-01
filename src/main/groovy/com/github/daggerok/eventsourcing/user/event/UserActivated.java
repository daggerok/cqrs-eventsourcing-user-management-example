package com.github.daggerok.eventsourcing.user.event;

import com.github.daggerok.eventsourcing.user.UserStatus;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@ToString
@RequiredArgsConstructor
public class UserActivated implements DomainEvent {
    @NonNull UUID aggregateId;
    final UserStatus state = UserStatus.ACTIVE;
    final ZonedDateTime at = ZonedDateTime.now();
}
