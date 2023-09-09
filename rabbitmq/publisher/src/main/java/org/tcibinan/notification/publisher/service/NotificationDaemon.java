package org.tcibinan.notification.publisher.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.publisher.message.Notification;
import org.tcibinan.notification.publisher.message.NotificationType;

import java.util.Random;
import java.util.UUID;

@Component
public class NotificationDaemon {

    private static final Random RANDOM = new Random(12345L);
    private static final int FAIL_PERCENT = 20;

    private final NotificationPublisher publisher;

    public NotificationDaemon(final NotificationPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 10_000)
    public void publish() {
        final String message = UUID.randomUUID().toString();
        final String receiver = isFail() ? null : "example@example.com";
        final NotificationType type = nextNotificationType();
        final Notification notification = new Notification(message, type, receiver, 0, null);
        publisher.publish(notification);
    }

    private static boolean isFail() {
        return RANDOM.nextInt(0, 100) < FAIL_PERCENT;
    }

    private NotificationType nextNotificationType() {
        return NotificationType.values()[RANDOM.nextInt(NotificationType.values().length)];
    }
}
