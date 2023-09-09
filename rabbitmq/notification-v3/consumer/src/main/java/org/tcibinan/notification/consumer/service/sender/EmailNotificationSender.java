package org.tcibinan.notification.consumer.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;

import java.time.Duration;

public class EmailNotificationSender implements NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailNotificationSender.class);

    private final Duration delay = Duration.ofSeconds(1L);

    @Override
    public void send(final Notification notification) {
        if (notification.receiver() == null) {
            throw new NotificationSendingException("Notification receiver is missing");
        }
        delay();
        LOGGER.info("Email notification has been sent: {}", notification);
    }

    private void delay() {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException ex) {
            throw new NotificationSendingException("Notification sending has been interrupted");
        }
    }
}
