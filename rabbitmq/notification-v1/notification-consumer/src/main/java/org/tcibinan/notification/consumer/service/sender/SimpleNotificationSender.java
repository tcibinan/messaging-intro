package org.tcibinan.notification.consumer.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;

public class SimpleNotificationSender implements NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleNotificationSender.class);

    @Override
    public void send(final Notification notification) {
        if (notification.receiver() == null) {
            throw new NotificationSendingException("Notification receiver is missing");
        }
        LOGGER.info("Notification has been sent: {}", notification);
    }
}
