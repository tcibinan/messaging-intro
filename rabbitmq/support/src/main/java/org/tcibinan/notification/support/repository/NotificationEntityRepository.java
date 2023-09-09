package org.tcibinan.notification.support.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.tcibinan.notification.support.entity.NotificationEntity;
import org.tcibinan.notification.support.entity.NotificationEntityStatus;

import java.util.List;

@Repository
@RepositoryRestResource(collectionResourceRel = "notifications", path = "notifications")
public interface NotificationEntityRepository extends CrudRepository<NotificationEntity, Long> {

    List<NotificationEntity> findByStatus(NotificationEntityStatus status);
}
