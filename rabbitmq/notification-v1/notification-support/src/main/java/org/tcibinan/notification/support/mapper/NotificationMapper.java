package org.tcibinan.notification.support.mapper;

import org.springframework.stereotype.Component;
import org.tcibinan.notification.support.entity.NotificationEntity;
import org.tcibinan.notification.support.entity.NotificationEntityType;
import org.tcibinan.notification.support.message.Notification;
import org.tcibinan.notification.support.message.NotificationType;

@Component
public class NotificationMapper {

    public Notification map(final NotificationEntity entity) {
        return new Notification(entity.getMessage(), map(entity.getType()), entity.getReceiver());
    }

    public NotificationType map(final NotificationEntityType type) {
        return NotificationType.valueOf(type.name());
    }

    public NotificationEntity map(final Notification notification) {
        final NotificationEntity entity = new NotificationEntity();
        entity.setMessage(notification.message());
        entity.setType(map(notification.type()));
        entity.setReceiver(notification.receiver());
        return entity;
    }

    private NotificationEntityType map(final NotificationType type) {
        return NotificationEntityType.valueOf(type.name());
    }
}
