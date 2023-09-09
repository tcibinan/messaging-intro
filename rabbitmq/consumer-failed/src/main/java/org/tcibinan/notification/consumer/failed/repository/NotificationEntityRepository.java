package org.tcibinan.notification.consumer.failed.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tcibinan.notification.consumer.failed.entity.NotificationEntity;

@Repository
public interface NotificationEntityRepository extends CrudRepository<NotificationEntity, Long> {
}
