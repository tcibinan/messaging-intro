package org.tcibinan.kafka.vehicle.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.kafka.vehicle.message.VehicleInput;

@Component
public class InputProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(InputProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public InputProducer(final KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(final VehicleInput input) {
        LOGGER.info("Producing {}...", input);
        kafkaTemplate.send("input", input.id(), input);
    }
}
