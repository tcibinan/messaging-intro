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
