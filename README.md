# CQRS and event sourcing app [![Build Status](https://travis-ci.org/daggerok/cqrs-eventsourcing-user-management-example.svg?branch=master)](https://travis-ci.org/daggerok/cqrs-eventsourcing-user-management-example)
CQRS and event sourcing using dynamic groovy, spring-boot and spring-webflux

Status: _in progress, implemented in-memory event store only, follow updates..._

_run kafka_

```bash
rm -rf /tmp/c ; git clone --depth=1 https://github.com/confluentinc/cp-docker-images.git /tmp/c
docker-compose -f /tmp/c/examples/kafka-single-node/docker-compose.yml up -d
docker-compose -f /tmp/c/examples/kafka-single-node/docker-compose.yml down -v --rmi local
```

_run app and test_

```bash
./gradlew bootRun

http :8080/api/v1/messages message=hello
http :8080/api/v1/messages message=world
http :8080/api/v1/messages
http :8080
```

**NOTE:** _For better developer experience during testing, use idea cURL integration tests from `rest-client*` files. Read more: https://www.jetbrains.com/help/idea/http-client-in-product-code-editor.html_

## why?

just see how everything is clear in next analitics:

- OrderCreated(id=123)
- ItemAdded(product=Bose Headphones, price=400)
- FollowSimilarItemsLink(product=Sony Headphones)
- AccessReviews()
- VoteReviewHelpful(answer=5/5)
- ItemAdded(product=Sony Headphones, price=450)
- ItemRemoved(product=Bose Headphones, price=400)
- OrderConfirmed()
- OrderShipped()

resources:

* [YouTube: Building Event Driven Systems with Spring Cloud Stream](https://www.youtube.com/watch?v=LvmPa7YKgqM&t=2673s)
* [YouTube: 2018-10 Advanced Microservices Patterns: CQRS and Event Sourcing](https://www.youtube.com/watch?v=W_wySQ0lTI4&t=1448s)
