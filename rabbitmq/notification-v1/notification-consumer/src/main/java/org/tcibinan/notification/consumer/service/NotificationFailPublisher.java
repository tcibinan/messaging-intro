package org.tcibinan.notification.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.config.RabbitMQConfiguration;
import org.tcibinan.notification.consumer.message.Notification;
import org.tcibinan.notification.consumer.message.NotificationFail;

@Component
public class NotificationFailPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationFailPublisher.class);

    private final RabbitTemplate template;

    public NotificationFailPublisher(final RabbitTemplate template) {
        this.template = template;
    }

    public void publish(NotificationFail notificationFail) {
        LOGGER.info("Publishing {}...", notificationFail);
        template.convertAndSend(RabbitMQConfiguration.failExchange, RabbitMQConfiguration.failAllRoutingKey,
                notificationFail, message -> {
                    message.getMessageProperties().getHeaders().put("__TypeId__", "notification.fail");
                    return message;
                });
    }
}
