package org.tcibinan.notification.consumer.dead.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.consumer.dead.config.RabbitMQConfiguration;
import org.tcibinan.notification.consumer.dead.repository.NotificationEntityRepository;
import org.tcibinan.notification.consumer.dead.entity.NotificationEntity;
import org.tcibinan.notification.consumer.dead.entity.NotificationEntityStatus;
import org.tcibinan.notification.consumer.dead.mapper.NotificationMapper;
import org.tcibinan.notification.consumer.dead.message.Notification;

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

    @RabbitListener(queues = RabbitMQConfiguration.deadAllQueue)
    public void receive(final Notification notification) {
        LOGGER.info("Received {}.", notification);
        final NotificationEntity notificationEntity = mapper.map(notification);
        notificationEntity.setStatus(NotificationEntityStatus.PENDING);
        notificationEntity.setReason("Notification queue is under pressure");
        LOGGER.info("Persisting {}...", notificationEntity);
        repository.save(notificationEntity);
    }
}
