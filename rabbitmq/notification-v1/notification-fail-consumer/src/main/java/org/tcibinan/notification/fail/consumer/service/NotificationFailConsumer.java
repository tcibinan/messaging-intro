package org.tcibinan.notification.fail.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.fail.consumer.entity.NotificationEntity;
import org.tcibinan.notification.fail.consumer.entity.NotificationEntityStatus;
import org.tcibinan.notification.fail.consumer.mapper.NotificationMapper;
import org.tcibinan.notification.fail.consumer.message.NotificationFail;
import org.tcibinan.notification.fail.consumer.repository.NotificationEntityRepository;

@Component
public class NotificationFailConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationFailConsumer.class);

    private final NotificationEntityRepository repository;
    private final NotificationMapper mapper;

    public NotificationFailConsumer(final NotificationEntityRepository repository,
                                    final NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void receive(final NotificationFail notificationFail) {
        LOGGER.info("Received {}.", notificationFail);
        final NotificationEntity notificationEntity = mapper.map(notificationFail.notification());
        notificationEntity.setFailReason(notificationFail.reason());
        notificationEntity.setStatus(NotificationEntityStatus.PENDING);
        LOGGER.info("Persisting {}...", notificationEntity);
        repository.save(notificationEntity);
    }
}
