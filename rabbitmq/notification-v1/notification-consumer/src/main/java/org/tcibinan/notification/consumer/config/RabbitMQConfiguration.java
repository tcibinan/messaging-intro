package org.tcibinan.notification.consumer.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.ClassMapper;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tcibinan.notification.consumer.message.Notification;
import org.tcibinan.notification.consumer.service.NotificationConsumer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    public static final String backlogExchange = "notification.backlog";
    public static final String backlogAllBinding = "notification.backlog.*";
    public static final String backlogAllQueue = "notification.backlog.all";

    public static final String failExchange = "notification.failed";
    public static final String failAllRoutingKey = "notification.failed.all";

    @Bean
    TopicExchange backlogExchange() {
        return new TopicExchange(backlogExchange);
    }

    @Bean
    Queue backlogAllQueue() {
        return new Queue(backlogAllQueue, false);
    }

    @Bean
    Binding backlogAllBinding(Queue backlogAllQueue, TopicExchange backlogExchange) {
        return BindingBuilder.bind(backlogAllQueue).to(backlogExchange).with(backlogAllBinding);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(backlogAllQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(NotificationConsumer subscriber,
                                           MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber);
        adapter.setDefaultListenerMethod("receive");
        adapter.setMessageConverter(messageConverter);
        return adapter;
    }

    @Bean
    TopicExchange failExchange() {
        return new TopicExchange(failExchange);
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(ClassMapper classMapper) {
        final Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public DefaultClassMapper classMapper() {
        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("notification", Notification.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
