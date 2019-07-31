package com.github.daggerok.eventsourcing.user

import spock.lang.Specification

class UserRepositoryTest extends Specification {

    def userRepository = new InMemoryUserRepository()

    def 'save user operation should flush user eventStream'() {
        given:
            def user = new User(UUID.randomUUID())
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
            def user = new User(userId)
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
