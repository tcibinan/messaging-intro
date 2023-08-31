package org.tcibinan.activemq.equation.requester.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.tcibinan.activemq.equation.message.Equation;
import org.tcibinan.activemq.equation.message.EquationOperator;

import java.util.Random;

@Component
public class EquationDaemon {

    private static final Random RANDOM = new Random(12345L);

    private final EquationPublisher publisher;

    public EquationDaemon(EquationPublisher publisher) {
        this.publisher = publisher;
    }

    @Scheduled(fixedDelay = 10_000)
    public void publish() {
        Equation equation = new Equation(RANDOM.nextLong(), RANDOM.nextLong(), nextEquationOperator());
        publisher.publish(equation);
    }

    private EquationOperator nextEquationOperator() {
        return EquationOperator.values()[RANDOM.nextInt(EquationOperator.values().length)];
    }
}
