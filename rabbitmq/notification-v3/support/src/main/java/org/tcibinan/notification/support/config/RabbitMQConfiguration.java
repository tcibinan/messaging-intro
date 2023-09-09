package org.tcibinan.notification.support.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.support.amqp.CustomTypeIdMessagePostProcessor;
import org.tcibinan.notification.support.message.Notification;

@Configuration
public class RabbitMQConfiguration {

    public static final String backlogExchange = "notification.backlog";
    public static final String backlogEmailReturnedRoutingKey = "notification.backlog.email.returned";
    public static final String backlogPushReturnedRoutingKey = "notification.backlog.push.returned";

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.topicExchange(backlogExchange).build();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public MessagePostProcessor notificationMessagePostProcessor() {
        return new CustomTypeIdMessagePostProcessor(Notification.class);
    }
}
