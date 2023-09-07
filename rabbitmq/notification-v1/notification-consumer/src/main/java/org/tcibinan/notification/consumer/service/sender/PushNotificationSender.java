package org.tcibinan.notification.consumer.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;

public class PushNotificationSender implements NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(PushNotificationSender.class);

    @Override
    public void send(final Notification notification) {
        if (notification.receiver() == null) {
            throw new NotificationSendingException("Push notification receiver is missing");
        }
        LOGGER.info("Push notification has been sent: {}", notification);
    }
}
