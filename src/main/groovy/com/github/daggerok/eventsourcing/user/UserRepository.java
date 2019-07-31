package com.github.daggerok.eventsourcing.user;

import java.util.UUID;

public interface UserRepository {
    void save(User user);
    User find(UUID userId);
}
