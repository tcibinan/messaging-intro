package org.tcibinan.notification.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
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

    public void publish(final NotificationFail notificationFail) {
        LOGGER.info("Publishing {}...", notificationFail);
        publish(notificationFail, RabbitMQConfiguration.failExchange, RabbitMQConfiguration.failAllRoutingKey);
    }

    private void publish(final NotificationFail notificationFail, final String exchange, final String routingKey) {
        template.convertAndSend(exchange, routingKey, notificationFail, new CustomTypeIdMessagePostProcessor());
    }

    public static class CustomTypeIdMessagePostProcessor implements MessagePostProcessor {

        @Override
        public Message postProcessMessage(final Message message) throws AmqpException {
            message.getMessageProperties().getHeaders().put("__TypeId__", "notification.fail");
            return message;
        }
    }
}
