package org.tcibinan.notification.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.consumer.service.sender.NotificationSender;
import org.tcibinan.notification.consumer.service.sender.PushNotificationSender;
import org.tcibinan.notification.consumer.service.sender.RetryingNotificationSender;
import org.tcibinan.notification.consumer.service.sender.EmailNotificationSender;

import java.time.Duration;

@Configuration
public class NotificationConfiguration {

    @Bean
    public NotificationSender emailSender() {
        final NotificationSender sender = new EmailNotificationSender();
        return new RetryingNotificationSender(sender, 5, Duration.ofSeconds(1));
    }

    @Bean
    public NotificationSender pushSender() {
        final NotificationSender sender = new PushNotificationSender();
        return new RetryingNotificationSender(sender, 5, Duration.ofSeconds(1));
    }
}
