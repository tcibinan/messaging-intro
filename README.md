# Messaging Intro

## ActiveMQ

### Pub Subs

> ActiveMQ console (user: admin, pass: admin) http://localhost:8761

```bash
./gradlew activemq:pub-subs:composeUp
./gradlew activemq:pub-subs:composeDown
```

### Request Reply

> ActiveMQ console (user: admin, pass: admin) http://localhost:8761

```bash
./gradlew activemq:request-reply:composeUp
./gradlew activemq:request-reply:composeDown
```

### Virtual Topic

> ActiveMQ console (user: admin, pass: admin) http://localhost:8761

```bash
./gradlew activemq:virtual-topic:composeUp
./gradlew activemq:virtual-topic:composeDown
```

## RabbitMQ

> RabbitMQ console (user: guest, pass: guest) http://localhost:15672
> 
> Support service swagger http://localhost:8080/swagger-ui/index.html

```bash
./gradlew rabbitmq:composeUp
./gradlew rabbitmq:composeDown
```

## Kafka

### Producer Consumer

```bash
./gradlew kafka:producer-consumer:test
```

### Vehicle

> Vehicle service swagger http://localhost:8080/swagger-ui/index.html

```bash
./gradlew kafka:vehicle:composeUp
./gradlew kafka:vehicle:composeDown
```

### Streams

```bash
./gradlew kafka:streams:test

./gradlew kafka:streams:composeUp
./gradlew kafka:streams:composeDown
```
