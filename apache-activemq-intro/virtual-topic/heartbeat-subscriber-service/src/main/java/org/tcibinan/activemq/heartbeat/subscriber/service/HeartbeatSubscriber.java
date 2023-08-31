package org.tcibinan.activemq.heartbeat.subscriber.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.heartbeat.message.Heartbeat;

@Component
public class HeartbeatSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(HeartbeatSubscriber.class);

    @JmsListener(destination = "Consumer.${heartbeat.subscriber.service.client.group}.VirtualTopic.heartbeat.queue")
    public void receive(Heartbeat heartbeat) {
        LOGGER.info("Received {}.", heartbeat);
    }
}
