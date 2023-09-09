package org.tcibinan.notification.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.consumer.amqp.CustomTypeIdMessagePostProcessor;
import org.tcibinan.notification.consumer.message.Notification;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    public static final String backlogExchange = "notification.backlog";
    public static final String backlogEmailQueue = "notification.backlog.email";
    public static final String backlogEmailBinding = "notification.backlog.email.*";
    public static final String backlogPushQueue = "notification.backlog.push";
    public static final String backlogPushBinding = "notification.backlog.push.*";

    public static final String backlogEmailRetriedRoutingKey = "notification.backlog.email.retried";
    public static final String backlogPushRetriedRoutingKey = "notification.backlog.push.retried";

    public static final String retryExchange = "notification.retry";
    public static final String retryEmailQueue = "notification.retry.email";
    public static final String retryEmailBinding = "notification.retry.email.submitted";
    public static final String retryEmailRoutingKey = retryEmailBinding;
    public static final String retryPushQueue = "notification.retry.push";
    public static final String retryPushBinding = "notification.retry.push.submitted";
    public static final String retryPushRoutingKey = retryPushBinding;

    public static final String failedExchange = "notification.failed";
    public static final String failedAllRoutingKey = "notification.failed.all";

    public static final String deadExchange = "notification.dead";
    public static final String deadAllRoutingKey = "notification.dead.all";

    @Bean
    public Exchange backlogExchange() {
        return ExchangeBuilder.topicExchange(backlogExchange).build();
    }

    @Bean
    public Queue backlogEmailQueue() {
        return QueueBuilder.durable(backlogEmailQueue)
                .maxLength(10)
                .overflow(QueueBuilder.Overflow.rejectPublish)
                .ttl(10_000)
                .deadLetterExchange(deadExchange)
                .deadLetterRoutingKey(deadAllRoutingKey)
                .build();
    }

    @Bean
    public Binding backlogEmailBinding(Queue backlogEmailQueue, Exchange backlogExchange) {
        return BindingBuilder.bind(backlogEmailQueue).to(backlogExchange).with(backlogEmailBinding).noargs();
    }

    @Bean
    public Queue backlogPushQueue() {
        return QueueBuilder.durable(backlogPushQueue)
                .maxLength(10)
                .overflow(QueueBuilder.Overflow.rejectPublish)
                .ttl(10_000)
                .deadLetterExchange(deadExchange)
                .deadLetterRoutingKey(deadAllRoutingKey)
                .build();
    }

    @Bean
    public Binding backlogPushBinding(Queue backlogPushQueue, Exchange backlogExchange) {
        return BindingBuilder.bind(backlogPushQueue).to(backlogExchange).with(backlogPushBinding).noargs();
    }

    @Bean
    public Exchange failedExchange() {
        return ExchangeBuilder.directExchange(failedExchange).build();
    }

    @Bean
    public Exchange deadExchange() {
        return ExchangeBuilder.directExchange(deadExchange).build();
    }

    @Bean
    public Exchange retryExchange() {
        return ExchangeBuilder.directExchange(retryExchange).build();
    }

    @Bean
    public Queue retryEmailQueue() {
        return QueueBuilder.durable(retryEmailQueue)
                .ttl(1_000)
                .deadLetterExchange(backlogExchange)
                .deadLetterRoutingKey(backlogEmailRetriedRoutingKey)
                .build();
    }

    @Bean
    public Binding retryEmailBinding(Queue retryEmailQueue, Exchange retryExchange) {
        return BindingBuilder.bind(retryEmailQueue).to(retryExchange).with(retryEmailBinding).noargs();
    }

    @Bean
    public Queue retryPushQueue() {
        return QueueBuilder.durable(retryPushQueue)
                .ttl(1_000)
                .deadLetterExchange(backlogExchange)
                .deadLetterRoutingKey(backlogPushRetriedRoutingKey)
                .build();
    }

    @Bean
    public Binding retryPushBinding(Queue retryPushQueue, Exchange retryExchange) {
        return BindingBuilder.bind(retryPushQueue).to(retryExchange).with(retryPushBinding).noargs();
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

    @Bean
    public MessagePostProcessor notificationMessagePostProcessor() {
        return new CustomTypeIdMessagePostProcessor(Notification.class);
    }
}
