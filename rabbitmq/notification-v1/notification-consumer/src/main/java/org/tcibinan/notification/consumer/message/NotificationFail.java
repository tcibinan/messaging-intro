package org.tcibinan.notification.consumer.message;

public record NotificationFail(String reason,
                               Notification notification) {
}
