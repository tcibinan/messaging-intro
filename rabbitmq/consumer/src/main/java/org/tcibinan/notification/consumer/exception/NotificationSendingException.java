package org.tcibinan.notification.consumer.exception;

public class NotificationSendingException extends RuntimeException {

    public NotificationSendingException(final String message) {
        super(message);
    }
}
