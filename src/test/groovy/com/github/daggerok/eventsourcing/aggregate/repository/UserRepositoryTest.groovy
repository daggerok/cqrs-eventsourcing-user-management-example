package com.github.daggerok.eventsourcing.aggregate.repository

import com.github.daggerok.eventsourcing.aggregate.User
import com.github.daggerok.eventsourcing.aggregate.UserStatus
import spock.lang.Specification

class UserRepositoryTest extends Specification {

    def userRepository = new InMemoryUserRepository()

    def 'save user operation should flush user eventStream'() {
        given:
            def user = new User()
        and:
            user.create()
        and:
            user.activate()
        when:
            userRepository.save(user)
        then:
            user.eventStream.size() == 0
    }

    def 'find operation should recreate user state'() {
        given:
            def userId = UUID.randomUUID()
        and:
            def user = new User()
        and:
            user.create(userId)
        and:
            user.activate()
        and:
            userRepository.save(user)
        when:
            def recreatedUser = userRepository.find(userId)
        then:
            recreatedUser.state == UserStatus.ACTIVE
    }
}
