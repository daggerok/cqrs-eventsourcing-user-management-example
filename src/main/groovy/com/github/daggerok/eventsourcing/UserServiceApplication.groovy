package com.github.daggerok.eventsourcing

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.messaging.Processor

@SpringBootApplication
@EnableBinding(Processor.class)
class UserServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(UserServiceApplication, args)
    }
}
