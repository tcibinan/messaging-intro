# Messaging Intro

## ActiveMQ

### Pub Subs

```bash
./gradlew apache-activemq-intro:pub-subs:composeUp

# ActiveMQ console (user: admin, pass: admin)
# http://localhost:8761

sleep 30

docker stop durable-heartbeat-subscriber-service

sleep 30

./gradlew apache-activemq-intro:pub-subs:composeUp

sleep 10

./gradlew apache-activemq-intro:pub-subs:composeLogs

# Check that both subscribers received all heartbeats
less apache-activemq-intro/pub-subs/build/containers-logs/durable-heartbeat-subscriber-service.log
less apache-activemq-intro/pub-subs/build/containers-logs/non-durable-heartbeat-subscriber-service.log 

./gradlew apache-activemq-intro:pub-subs:composeDown
```

### Request Replier

```bash
./gradlew apache-activemq-intro:request-reply:composeUp

# ActiveMQ console (user: admin, pass: admin)
# http://localhost:8761

sleep 30

./gradlew apache-activemq-intro:request-reply:composeLogs

# Check that both subscribers received all heartbeats
less apache-activemq-intro/request-reply/build/containers-logs/equation-requester-service.log
less apache-activemq-intro/request-reply/build/containers-logs/equation-replier-service.log

./gradlew apache-activemq-intro:pub-subs:composeDown
```

### Virtual Topic

```bash
./gradlew apache-activemq-intro:virtual-topic:composeUp

# ActiveMQ console (user: admin, pass: admin)
# http://localhost:8761

sleep 30

./gradlew apache-activemq-intro:virtual-topic:composeLogs

# Check that two instances received different heartbeats
less apache-activemq-intro/virtual-topic/build/containers-logs/first-heartbeat-subscriber-service.log

# Check that two instances received different heartbeats
less apache-activemq-intro/virtual-topic/build/containers-logs/second-heartbeat-subscriber-service.log

./gradlew apache-activemq-intro:pub-subs:composeDown
```
