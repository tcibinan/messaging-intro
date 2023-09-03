package org.tcibinan.activemq.equation.replier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.equation.message.Equation;
import org.tcibinan.activemq.equation.message.EquationResult;

@Component
public class EquationSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquationSubscriber.class);

    private final EquationResultPublisher publisher;

    public EquationSubscriber(final EquationResultPublisher publisher) {
        this.publisher = publisher;
    }

    @JmsListener(destination = "equation.queue")
    public void receive(Equation equation) {
        LOGGER.info("Received {}.", equation);
        Long result = switch (equation.operator()) {
            case PLUS -> equation.left() + equation.right();
            case MINUS -> equation.left() - equation.right();
        };
        publisher.publish(new EquationResult(result, equation));
    }
}
