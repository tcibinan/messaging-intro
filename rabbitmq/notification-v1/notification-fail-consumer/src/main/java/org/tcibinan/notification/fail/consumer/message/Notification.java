package org.tcibinan.notification.fail.consumer.message;

public record Notification(String message,
                           NotificationType type,
                           String receiver) {
}
