package org.tcibinan.notification.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.config.RabbitMQConfiguration;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;
import org.tcibinan.notification.consumer.service.sender.NotificationSender;

@Component
public class NotificationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationSender emailSender;
    private final NotificationSender pushSender;
    private final NotificationFailedPublisher failedPublisher;
    private final NotificationRetryPublisher retryPublisher;

    public NotificationConsumer(final NotificationSender emailSender,
                                final NotificationSender pushSender,
                                final NotificationFailedPublisher failedPublisher,
                                final NotificationRetryPublisher retryPublisher) {
        this.emailSender = emailSender;
        this.pushSender = pushSender;
        this.failedPublisher = failedPublisher;
        this.retryPublisher = retryPublisher;
    }

    @RabbitListener(queues = {RabbitMQConfiguration.backlogEmailQueue, RabbitMQConfiguration.backlogPushQueue})
    public void receive(final Notification notification) {
        LOGGER.info("Received {}.", notification);
        try {
            switch (notification.type()) {
                case EMAIL -> emailSender.send(notification);
                case PUSH -> pushSender.send(notification);
            }
        } catch (NotificationSendingException e) {
            final Notification failedNotification = fail(notification, e.getMessage());
            if (failedNotification.retries() < 5) {
                retryPublisher.publish(failedNotification);
            } else {
                failedPublisher.publish(failedNotification);
            }
        }
    }

    private Notification fail(final Notification notification, final String message) {
        return new Notification(notification.message(), notification.type(), notification.receiver(),
                notification.retries() + 1, message);
    }
}
