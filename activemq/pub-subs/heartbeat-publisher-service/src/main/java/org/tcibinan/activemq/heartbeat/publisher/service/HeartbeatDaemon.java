package org.tcibinan.activemq.heartbeat.publisher.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.heartbeat.message.Heartbeat;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class HeartbeatDaemon {

    private final HeartbeatPublisher publisher;

    public HeartbeatDaemon(HeartbeatPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 10_000)
    public void publish() {
        publisher.publish(new Heartbeat("heartbeat-publisher-service", LocalDateTime.now(ZoneOffset.UTC)));
    }
}
