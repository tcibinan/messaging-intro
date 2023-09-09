package org.tcibinan.notification.support.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tcibinan.notification.support.entity.NotificationEntity;
import org.tcibinan.notification.support.entity.NotificationEntityStatus;
import org.tcibinan.notification.support.mapper.NotificationMapper;
import org.tcibinan.notification.support.message.Notification;
import org.tcibinan.notification.support.repository.NotificationEntityRepository;

@Component
public class NotificationDaemon {

    private final NotificationEntityRepository repository;
    private final NotificationPublisher publisher;
    private final NotificationMapper mapper;

    public NotificationDaemon(final NotificationEntityRepository repository,
                              final NotificationPublisher publisher,
                              final NotificationMapper mapper) {
        this.repository = repository;
        this.publisher = publisher;
        this.mapper = mapper;
    }

    @Scheduled(fixedDelay = 10_000)
    void publish() {
        for (NotificationEntity entity : repository.findByStatus(NotificationEntityStatus.RETURNING)) {
            Notification notification = mapper.map(entity);
            publisher.publish(notification);
            entity.setStatus(NotificationEntityStatus.RETURNED);
            repository.save(entity);
        }
    }
}
