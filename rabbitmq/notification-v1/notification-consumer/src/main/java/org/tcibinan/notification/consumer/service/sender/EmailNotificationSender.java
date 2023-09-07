package org.tcibinan.notification.consumer.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;

public class EmailNotificationSender implements NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationSender.class);

    @Override
    public void send(final Notification notification) {
        if (notification.receiver() == null) {
            throw new NotificationSendingException("Email notification receiver is missing");
        }
        LOGGER.info("Email notification has been sent: {}", notification);
    }
}
