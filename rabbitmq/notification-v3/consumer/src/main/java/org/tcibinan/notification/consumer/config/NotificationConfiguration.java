package org.tcibinan.notification.consumer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.consumer.service.sender.NotificationSender;
import org.tcibinan.notification.consumer.service.sender.PushNotificationSender;
import org.tcibinan.notification.consumer.service.sender.EmailNotificationSender;

@Configuration
public class NotificationConfiguration {

    @Bean
    public NotificationSender emailSender() {
        return new EmailNotificationSender();
    }

    @Bean
    public NotificationSender pushSender() {
        return new PushNotificationSender();
    }
}
