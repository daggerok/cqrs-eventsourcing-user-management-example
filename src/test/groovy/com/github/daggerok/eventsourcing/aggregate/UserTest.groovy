package com.github.daggerok.eventsourcing.aggregate

import spock.lang.Specification

class UserTest extends Specification {

    def 'created user should have pending state'() {
        given:
            def user = new User()
        when:
            user.create()
        then:
            user.state == UserStatus.PENDING
    }

    def 'active user should not be activated'() {
        given:
            def user = new User()
        and:
            user.create()
        and:
            user.activate()
        when:
            user.activate()
        then:
            thrown(IllegalStateException)
    }

    def 'created user can be activated'() {
        given:
            def user = new User()
        and:
            user.create()
        when:
            user.activate()
        then:
            user.state == UserStatus.ACTIVE
    }

    def 'suspended user cannot be deactivated'() {
        given:
            def user = new User()
        and:
            user.create()
        and:
            user.deactivate()
        when:
            user.deactivate()
        then:
            thrown(IllegalStateException)
    }

    def 'active user can be deactivated'() {
        given:
            def user = new User()
        and:
            user.create()
        and:
            user.activate()
        when:
            user.deactivate()
        then:
            user.state == UserStatus.SUSPENDED
    }
}
