package org.tcibinan.notification.support.message;

public record Notification(String message,
                           NotificationType type,
                           String receiver,
                           int retries,
                           String reason) {
}
