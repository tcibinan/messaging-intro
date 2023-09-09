package org.tcibinan.notification.consumer.dead.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tcibinan.notification.consumer.dead.entity.NotificationEntity;

@Repository
public interface NotificationEntityRepository extends CrudRepository<NotificationEntity, Long> {
}
