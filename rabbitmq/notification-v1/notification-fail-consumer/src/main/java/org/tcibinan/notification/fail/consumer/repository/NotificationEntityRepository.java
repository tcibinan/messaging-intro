package org.tcibinan.notification.fail.consumer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.tcibinan.notification.fail.consumer.entity.NotificationEntity;

@Repository
public interface NotificationEntityRepository extends CrudRepository<NotificationEntity, Long> {
}
