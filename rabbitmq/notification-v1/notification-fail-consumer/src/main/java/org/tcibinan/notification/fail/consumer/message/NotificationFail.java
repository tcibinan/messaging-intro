package org.tcibinan.notification.fail.consumer.message;

public record NotificationFail(String reason,
                               Notification notification) {
}
