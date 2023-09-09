package org.tcibinan.notification.publisher.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.publisher.amqp.CustomTypeIdMessagePostProcessor;
import org.tcibinan.notification.publisher.message.Notification;

@Configuration
public class RabbitMQConfiguration {

    public static final String backlogExchange = "notification.backlog";
    public static final String backlogEmailSubmittedRoutingKey = "notification.backlog.email.submitted";
    public static final String backlogPushSubmittedRoutingKey = "notification.backlog.push.submitted";

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
