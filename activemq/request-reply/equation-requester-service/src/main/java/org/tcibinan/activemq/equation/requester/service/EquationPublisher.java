package org.tcibinan.activemq.equation.requester.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.equation.message.Equation;

@Component
public class EquationPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquationPublisher.class);

    private final JmsTemplate template;

    public EquationPublisher(JmsTemplate template) {
        this.template = template;
    }

    public void publish(final Equation equation) {
        LOGGER.info("Publishing equation {}...", equation);
        template.convertAndSend("equation.queue", equation);
    }
}
