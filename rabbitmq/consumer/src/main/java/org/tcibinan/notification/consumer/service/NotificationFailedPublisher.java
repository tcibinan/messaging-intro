package org.tcibinan.notification.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.config.RabbitMQConfiguration;
import org.tcibinan.notification.consumer.message.Notification;

@Component
public class NotificationFailedPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationFailedPublisher.class);

    private final RabbitTemplate template;
    private final MessagePostProcessor notificationMessagePostProcessor;

    public NotificationFailedPublisher(final RabbitTemplate template,
                                       final MessagePostProcessor notificationMessagePostProcessor) {
        this.template = template;
        this.notificationMessagePostProcessor = notificationMessagePostProcessor;
    }

    public void publish(final Notification notification) {
        LOGGER.info("Publishing to failed exchange {}...", notification);
        publish(notification, RabbitMQConfiguration.failedExchange, RabbitMQConfiguration.failedAllRoutingKey);
    }

    private void publish(final Notification notification, final String exchange, final String routingKey) {
        template.convertAndSend(exchange, routingKey, notification, notificationMessagePostProcessor);
    }
}
