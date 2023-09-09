package org.tcibinan.notification.consumer.failed.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name="notification")
public class NotificationEntity {

    @Id
    @GeneratedValue
    private Long id;
    private String message;
    private NotificationEntityType type;
    private String receiver;
    private String reason;
    private NotificationEntityStatus status;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public NotificationEntityType getType() {
        return type;
    }

    public void setType(final NotificationEntityType type) {
        this.type = type;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(final String receiver) {
        this.receiver = receiver;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(final String reason) {
        this.reason = reason;
    }

    public NotificationEntityStatus getStatus() {
        return status;
    }

    public void setStatus(final NotificationEntityStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NotificationEntity{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", type=" + type +
                ", receiver='" + receiver + '\'' +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
    }
}
