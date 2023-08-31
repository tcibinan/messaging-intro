package org.tcibinan.activemq.equation.requester.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.equation.message.EquationResult;

@Component
public class EquationResultSubscriber {

    private static final Logger LOGGER = LoggerFactory.getLogger(EquationResultSubscriber.class);

    @JmsListener(destination = "equation.result.queue")
    public void receive(EquationResult result) {
        LOGGER.info("Received {}.", result);
    }
}
