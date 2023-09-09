package org.tcibinan.notification.publisher.amqp;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class CustomTypeIdMessagePostProcessor implements MessagePostProcessor {

    private final Class<?> type;

    public CustomTypeIdMessagePostProcessor(final Class<?> type) {
        this.type = type;
    }

    @Override
    public Message postProcessMessage(final Message message) throws AmqpException {
        message.getMessageProperties().getHeaders().put("__TypeId__", type.getSimpleName());
        return message;
    }
}
