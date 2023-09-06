package org.tcibinan.notification.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;
import org.tcibinan.notification.consumer.message.NotificationFail;
import org.tcibinan.notification.consumer.service.sender.NotificationSender;

@Component
public class NotificationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationSender sender;
    private final NotificationFailPublisher failPublisher;

    public NotificationConsumer(final NotificationSender sender, final NotificationFailPublisher failPublisher) {
        this.sender = sender;
        this.failPublisher = failPublisher;
    }

    public void receive(final Notification notification) {
        LOGGER.info("Received {}.", notification);
        try {
            sender.send(notification);
        } catch (NotificationSendingException e) {
            NotificationFail notificationFail = new NotificationFail(e.getMessage(), notification);
            failPublisher.publish(notificationFail);
        }
    }
}
