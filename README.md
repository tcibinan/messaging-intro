# Messaging Intro

## ActiveMQ

### Pub Subs

```bash
./gradlew activemq:pub-subs:composeUp

# ActiveMQ console (user: admin, pass: admin)
# http://localhost:8761

sleep 30

docker stop durable-heartbeat-subscriber-service

sleep 30

./gradlew activemq:pub-subs:composeUp

sleep 30

./gradlew activemq:pub-subs:composeLogs

# Check that both subscribers have received all messages
less activemq/pub-subs/build/containers-logs/durable-heartbeat-subscriber-service.log
less activemq/pub-subs/build/containers-logs/non-durable-heartbeat-subscriber-service.log 

./gradlew activemq:pub-subs:composeDown
```

### Request Reply

```bash
./gradlew activemq:request-reply:composeUp

# ActiveMQ console (user: admin, pass: admin)
# http://localhost:8761

sleep 30

./gradlew activemq:request-reply:composeLogs

# Check that both services have received and published messages
less activemq/request-reply/build/containers-logs/equation-requester-service.log
less activemq/request-reply/build/containers-logs/equation-replier-service.log

./gradlew activemq:pub-subs:composeDown
```

### Virtual Topic

```bash
./gradlew activemq:virtual-topic:composeUp

# ActiveMQ console (user: admin, pass: admin)
# http://localhost:8761

sleep 30

./gradlew activemq:virtual-topic:composeLogs

# Check that both service instances have received unique messages
less activemq/virtual-topic/build/containers-logs/first-heartbeat-subscriber-service.log

# Check that both service instances have received unique messages
less activemq/virtual-topic/build/containers-logs/second-heartbeat-subscriber-service.log

./gradlew activemq:pub-subs:composeDown
```

## RabbitMQ

```bash
./gradlew rabbitmq:composeUp

# RabbitMQ console (user: guest, pass: guest)
# http://localhost:15672

# Support service swagger
# http://localhost:8080/swagger-ui/index.html

./gradlew rabbitmq:composeDown
```

## Kafka

```bash
./gradlew kafka:composeUp

./gradlew kafka:composeDown
```
