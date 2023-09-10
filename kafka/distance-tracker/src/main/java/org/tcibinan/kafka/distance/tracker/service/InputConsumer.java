package org.tcibinan.kafka.distance.tracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tcibinan.kafka.vehicle.message.VehicleInput;

@Component
public class InputConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputConsumer.class);

    private final InputProcessor inputProcessor;
    private final OutputProducer outputProducer;

    public InputConsumer(final InputProcessor inputProcessor, final OutputProducer outputProducer) {
        this.inputProcessor = inputProcessor;
        this.outputProducer = outputProducer;
    }

    @KafkaListener(topics = "input")
    public void consume(final VehicleInput input) {
        LOGGER.info("Consumed {}.", input);
        outputProducer.publish(inputProcessor.process(input));
    }
}
