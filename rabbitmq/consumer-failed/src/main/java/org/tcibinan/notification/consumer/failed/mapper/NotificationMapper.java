package org.tcibinan.notification.consumer.failed.mapper;

import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.failed.entity.NotificationEntity;
import org.tcibinan.notification.consumer.failed.entity.NotificationEntityType;
import org.tcibinan.notification.consumer.failed.message.Notification;
import org.tcibinan.notification.consumer.failed.message.NotificationType;

@Component
public class NotificationMapper {

    public Notification map(final NotificationEntity entity) {
        return new Notification(entity.getMessage(), map(entity.getType()), entity.getReceiver(), 0, null);
    }

    public NotificationType map(final NotificationEntityType type) {
        return NotificationType.valueOf(type.name());
    }

    public NotificationEntity map(final Notification notification) {
        final NotificationEntity entity = new NotificationEntity();
        entity.setMessage(notification.message());
        entity.setType(map(notification.type()));
        entity.setReceiver(notification.receiver());
        entity.setReason(notification.reason());
        return entity;
    }

    private NotificationEntityType map(final NotificationType type) {
        return NotificationEntityType.valueOf(type.name());
    }
}
