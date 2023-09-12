package org.tcibinan.kafka.producer.consumer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.tcibinan.kafka.producer.consumer.message.Metric;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

@Component
public class MetricConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(MetricConsumer.class);

    private final BlockingQueue<Metric> queue = new ArrayBlockingQueue<>(10);

    @KafkaListener(topics = "topic")
    public void consume(final Metric metric) {
        LOGGER.info("Consumed {}.", metric);
        queue.offer(metric);
    }

    public Metric take() throws InterruptedException {
        return queue.take();
    }
}
