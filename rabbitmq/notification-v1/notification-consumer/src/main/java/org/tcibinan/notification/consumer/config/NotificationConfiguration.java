package org.tcibinan.notification.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.consumer.service.sender.NotificationSender;
import org.tcibinan.notification.consumer.service.sender.RetryingNotificationSender;
import org.tcibinan.notification.consumer.service.sender.SimpleNotificationSender;

import java.time.Duration;

@Configuration
public class NotificationConfiguration {

    @Bean
    public NotificationSender notificationSender() {
        final NotificationSender sender = new SimpleNotificationSender();
        return new RetryingNotificationSender(sender, 5, Duration.ofSeconds(1));
    }
}
