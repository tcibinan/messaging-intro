package org.tcibinan.notification.support.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.support.config.RabbitMQConfiguration;
import org.tcibinan.notification.support.message.Notification;

@Component
public class NotificationPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationPublisher.class);

    private final RabbitTemplate template;

    public NotificationPublisher(final RabbitTemplate template) {
        this.template = template;
    }

    public void publish(final Notification notification) {
        LOGGER.info("Publishing {}...", notification);
        switch (notification.type()) {
            case EMAIL -> publish(notification,
                    RabbitMQConfiguration.backlogExchange,
                    RabbitMQConfiguration.backlogEmailReturnedRoutingKey);
            case PUSH -> publish(notification,
                    RabbitMQConfiguration.backlogExchange,
                    RabbitMQConfiguration.backlogPushReturnedRoutingKey);
        }
    }

    private void publish(final Notification notification, final String exchange, final String routingKey) {
        template.convertAndSend(exchange, routingKey, notification, new CustomTypeIdMessagePostProcessor());
    }

    public static class CustomTypeIdMessagePostProcessor implements MessagePostProcessor {

        @Override
        public Message postProcessMessage(final Message message) throws AmqpException {
            message.getMessageProperties().getHeaders().put("__TypeId__", "notification");
            return message;
        }
    }
}
