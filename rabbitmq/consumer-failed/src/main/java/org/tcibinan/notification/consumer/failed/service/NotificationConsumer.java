package org.tcibinan.notification.consumer.failed.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.failed.config.RabbitMQConfiguration;
import org.tcibinan.notification.consumer.failed.entity.NotificationEntity;
import org.tcibinan.notification.consumer.failed.entity.NotificationEntityStatus;
import org.tcibinan.notification.consumer.failed.mapper.NotificationMapper;
import org.tcibinan.notification.consumer.failed.message.Notification;
import org.tcibinan.notification.consumer.failed.repository.NotificationEntityRepository;

@Component
public class NotificationConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationConsumer.class);

    private final NotificationEntityRepository repository;
    private final NotificationMapper mapper;

    public NotificationConsumer(final NotificationEntityRepository repository,
                                final NotificationMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @RabbitListener(queues = RabbitMQConfiguration.failedAllQueue)
    public void receive(final Notification notification) {
        LOGGER.info("Received {}.", notification);
        final NotificationEntity notificationEntity = mapper.map(notification);
        notificationEntity.setStatus(NotificationEntityStatus.PENDING);
        LOGGER.info("Persisting {}...", notificationEntity);
        repository.save(notificationEntity);
    }
}
