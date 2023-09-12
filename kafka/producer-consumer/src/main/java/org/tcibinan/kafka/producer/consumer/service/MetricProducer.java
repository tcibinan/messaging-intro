package org.tcibinan.kafka.producer.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.tcibinan.kafka.producer.consumer.message.Metric;

@Component
public class MetricProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricProducer.class);

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public MetricProducer(final KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void produce(final Metric metric) {
        LOGGER.info("Producing {}...", metric);
        kafkaTemplate.send("topic", metric);
    }
}
