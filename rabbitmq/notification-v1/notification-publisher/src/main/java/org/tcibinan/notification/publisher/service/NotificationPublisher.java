package org.tcibinan.notification.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.publisher.config.RabbitMQConfiguration;
import org.tcibinan.notification.publisher.message.Notification;

@Component
public class NotificationPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPublisher.class);

    private final RabbitTemplate template;

    public NotificationPublisher(RabbitTemplate template) {
        this.template = template;
    }

    public void publish(Notification notification) {
        LOGGER.info("Publishing {}...", notification);
        template.convertAndSend(RabbitMQConfiguration.backlogExchange, RabbitMQConfiguration.backlogAllRoutingKey,
                notification, message -> {
            message.getMessageProperties().getHeaders().put("__TypeId__", "notification");
                    return message;
                });
    }
}
