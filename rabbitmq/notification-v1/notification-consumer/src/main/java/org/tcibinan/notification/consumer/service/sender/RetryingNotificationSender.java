package org.tcibinan.notification.consumer.service.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.exception.NotificationSendingException;
import org.tcibinan.notification.consumer.message.Notification;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RetryingNotificationSender implements NotificationSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryingNotificationSender.class);

    private final NotificationSender sender;
    private final int attempts;
    private final Duration delay;

    public RetryingNotificationSender(final NotificationSender sender,
                                      final int attempts,
                                      final Duration delay) {
        this.sender = sender;
        this.attempts = Math.max(1, attempts);
        this.delay = delay;
    }

    @Override
    public void send(final Notification notification) {
        final List<Exception> exceptions = new ArrayList<>();
        int attempts = this.attempts;
        while (attempts > 0) {
            attempts -= 1;
            try {
                sender.send(notification);
                return;
            } catch (NotificationSendingException e) {
                exceptions.add(e);
                LOGGER.warn("Notification has NOT been sent: {}. Reason: {}. Retrying in {} seconds...",
                        notification, e.getMessage(), delay.toSeconds());
                delay(e);
            }
        }
        final String reason = exceptions.stream()
                .map(Throwable::getMessage)
                .filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.joining(", "));
        throw new NotificationSendingException(reason);
    }

    private void delay(final NotificationSendingException e) {
        try {
            Thread.sleep(delay.toMillis());
        } catch (InterruptedException ex) {
            throw new NotificationSendingException(e.getMessage());
        }
    }
}
