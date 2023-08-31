package org.tcibinan.activemq.equation.replier.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.equation.message.EquationResult;

@Component
public class EquationResultPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquationResultPublisher.class);

    private final JmsTemplate template;

    public EquationResultPublisher(JmsTemplate template) {
        this.template = template;
    }

    public void publish(final EquationResult equation) {
        LOGGER.info("Publishing equation result {}...", equation);
        template.convertAndSend("equation.result.queue", equation);
    }
}
