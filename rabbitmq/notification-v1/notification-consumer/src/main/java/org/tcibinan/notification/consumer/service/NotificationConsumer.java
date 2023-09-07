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

    private final NotificationSender emailSender;
    private final NotificationSender pushSender;
    private final NotificationFailPublisher failPublisher;

    public NotificationConsumer(final NotificationSender emailSender,
                                final NotificationSender pushSender,
                                final NotificationFailPublisher failPublisher) {
        this.emailSender = emailSender;
        this.pushSender = pushSender;
        this.failPublisher = failPublisher;
    }

    public void receive(final Notification notification) {
        LOGGER.info("Received {}.", notification);
        try {
            switch (notification.type()) {
                case EMAIL -> emailSender.send(notification);
                case PUSH -> pushSender.send(notification);
            }
        } catch (NotificationSendingException e) {
            NotificationFail notificationFail = new NotificationFail(e.getMessage(), notification);
            failPublisher.publish(notificationFail);
        }
    }
}
