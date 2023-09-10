package org.tcibinan.kafka.distance.logger.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tcibinan.kafka.vehicle.message.VehicleOutput;

@Component
public class OutputConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(OutputConsumer.class);

    @KafkaListener(topics = "output")
    public void consume(final VehicleOutput output) {
        LOGGER.info("Consumed {}.", output);
    }
}
