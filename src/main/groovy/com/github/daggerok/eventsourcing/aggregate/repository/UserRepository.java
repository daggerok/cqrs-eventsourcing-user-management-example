package com.github.daggerok.eventsourcing.aggregate.repository;

import com.github.daggerok.eventsourcing.aggregate.User;

import java.util.UUID;

public interface UserRepository {
    void save(User user);

    User find(UUID userId);
}
