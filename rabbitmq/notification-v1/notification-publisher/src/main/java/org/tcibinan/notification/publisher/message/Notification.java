package org.tcibinan.notification.publisher.message;

public record Notification(String message,
                           NotificationType type,
                           String receiver) {
}
