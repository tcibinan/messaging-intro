package org.tcibinan.kafka.distance.tracker.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.kafka.vehicle.message.VehicleOutput;

@Component
public class OutputProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutputProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OutputProducer(final KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(final VehicleOutput output) {
        LOGGER.info("Producing {}...", output);
        kafkaTemplate.send("output", output);
    }
}
