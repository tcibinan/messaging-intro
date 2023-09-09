package org.tcibinan.notification.publisher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.publisher.config.RabbitMQConfiguration;
import org.tcibinan.notification.publisher.message.Notification;

@Component
public class NotificationPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPublisher.class);

    private final RabbitTemplate template;
    private final MessagePostProcessor notificationMessagePostProcessor;

    public NotificationPublisher(final RabbitTemplate template,
                                 final MessagePostProcessor notificationMessagePostProcessor) {
        this.template = template;
        this.notificationMessagePostProcessor = notificationMessagePostProcessor;
    }

    public void publish(final Notification notification) {
        LOGGER.info("Publishing {}...", notification);
        switch (notification.type()) {
            case EMAIL -> publish(notification,
                    RabbitMQConfiguration.backlogExchange,
                    RabbitMQConfiguration.backlogEmailSubmittedRoutingKey);
            case PUSH -> publish(notification,
                    RabbitMQConfiguration.backlogExchange,
                    RabbitMQConfiguration.backlogPushSubmittedRoutingKey);
        }
    }

    private void publish(final Notification notification, final String exchange, final String routingKey) {
        template.convertAndSend(exchange, routingKey, notification, notificationMessagePostProcessor);
    }
}
