package org.tcibinan.notification.consumer.failed.message;

public record Notification(String message,
                           NotificationType type,
                           String receiver,
                           int retries,
                           String reason) {
}
