package org.tcibinan.notification.consumer.message;

public record Notification(String message,
                           NotificationType type,
                           String receiver,
                           int retries,
                           String reason) {
}
