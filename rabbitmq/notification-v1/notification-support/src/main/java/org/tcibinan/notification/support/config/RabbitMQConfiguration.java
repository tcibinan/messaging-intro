package org.tcibinan.notification.support.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String backlogExchange = "notification.backlog";
    public static final String backlogEmailReturnedRoutingKey = "notification.backlog.email.returned";
    public static final String backlogPushReturnedRoutingKey = "notification.backlog.push.returned";

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(backlogExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
