package org.tcibinan.notification.consumer.dead.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.consumer.dead.message.Notification;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    public static final String deadExchange = "notification.dead";
    public static final String deadAllBinding = "notification.dead.all";
    public static final String deadAllQueue = "notification.dead.all";

    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange(deadExchange).build();
    }

    @Bean
    public Queue deadAllQueue() {
        return QueueBuilder.durable(deadAllQueue).build();
    }

    @Bean
    public Binding binding(Queue deadAllQueue, Exchange deadExchange) {
        return BindingBuilder.bind(deadAllQueue).to(deadExchange).with(deadAllBinding).noargs();
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ClassMapper classMapper) {
        final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        final DefaultClassMapper classMapper = new DefaultClassMapper();
        final Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put(Notification.class.getSimpleName(), Notification.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
