package org.tcibinan.activemq.heartbeat.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.heartbeat.message.Heartbeat;

@Component
public class HeartbeatPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatPublisher.class);

    private final JmsTemplate template;

    public HeartbeatPublisher(JmsTemplate template) {
        this.template = template;
    }

    public void publish(final Heartbeat heartbeat) {
        LOGGER.info("Publishing heartbeat {}...", heartbeat);
        template.convertAndSend("heartbeat.queue", heartbeat);
    }
}
