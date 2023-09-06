package org.tcibinan.notification.fail.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.fail.consumer.entity.NotificationEntity;
import org.tcibinan.notification.fail.consumer.entity.NotificationEntityStatus;
import org.tcibinan.notification.fail.consumer.message.Notification;
import org.tcibinan.notification.fail.consumer.message.NotificationFail;
import org.tcibinan.notification.fail.consumer.repository.NotificationEntityRepository;

@Component
public class NotificationFailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationFailConsumer.class);

    private final NotificationEntityRepository repository;

    public NotificationFailConsumer(NotificationEntityRepository repository) {
        this.repository = repository;
    }

    public void receive(NotificationFail notificationFail) {
        LOGGER.info("Received {}.", notificationFail);
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setFailReason(notificationFail.reason());
        notificationEntity.setMessage(notificationFail.notification().message());
        notificationEntity.setReceiver(notificationFail.notification().receiver());
        notificationEntity.setStatus(NotificationEntityStatus.PENDING);
        LOGGER.info("Persisting {}...", notificationEntity);
        repository.save(notificationEntity);
    }
}
