# CQRS and event sourcing app [![Build Status](https://travis-ci.org/daggerok/cqrs-eventsourcing-user-management-example.svg?branch=master)](https://travis-ci.org/daggerok/cqrs-eventsourcing-user-management-example)
CQRS and event sourcing using dynamic groovy, spring-boot and spring-webflux

Status: _in progress, implemented in-memory event store only, follow updates..._

```bash
./gradlew bootRun

http :8080/api/v1/messages message=hello
http :8080/api/v1/messages message=world
http :8080/api/v1/messages
http :8080
```

**NOTE:** _For better developer experience during testing, use idea cURL integration tests from `rest-client*` files. Read more: https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html_

resources:

* [YouTube: Building Event Driven Systems with Spring Cloud Stream](https://www.youtube.com/watch?v=LvmPa7YKgqM&t=2673s)
