package org.tcibinan.notification.consumer.service.sender;

import org.tcibinan.notification.consumer.message.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
