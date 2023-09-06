package org.tcibinan.notification.fail.consumer.config;

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
import org.tcibinan.notification.fail.consumer.message.NotificationFail;
import org.tcibinan.notification.fail.consumer.service.NotificationFailConsumer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfiguration {

    public static final String failExchange = "notification.failed";
    public static final String failAllBinding = "notification.failed.*";
    public static final String failAllQueue = "notification.failed.all";

    @Bean
    TopicExchange failExchange() {
        return new TopicExchange(failExchange);
    }

    @Bean
    Queue failAllQueue() {
        return new Queue(failAllQueue, false);
    }

    @Bean
    Binding binding(Queue failAllQueue, TopicExchange failExchange) {
        return BindingBuilder.bind(failAllQueue).to(failExchange).with(failAllBinding);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(failAllQueue);
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(NotificationFailConsumer subscriber,
                                           MessageConverter messageConverter) {
        MessageListenerAdapter adapter = new MessageListenerAdapter(subscriber);
        adapter.setDefaultListenerMethod("receive");
        adapter.setMessageConverter(messageConverter);
        return adapter;
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
        idClassMapping.put("notification.fail", NotificationFail.class);
        classMapper.setIdClassMapping(idClassMapping);
        return classMapper;
    }
}
