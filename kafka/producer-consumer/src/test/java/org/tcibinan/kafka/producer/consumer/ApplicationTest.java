package org.tcibinan.kafka.producer.consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.tcibinan.kafka.producer.consumer.message.Metric;
import org.tcibinan.kafka.producer.consumer.service.MetricConsumer;
import org.tcibinan.kafka.producer.consumer.service.MetricProducer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class ApplicationTest {

    @Container
    private static final KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.5.0"));

    @DynamicPropertySource
    public static void kafkaProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.consumer.group-id", () -> "consumer");
    }

    @Autowired
    private MetricConsumer consumer;

    @Autowired
    private MetricProducer producer;

    @Test
    @Timeout(value = 10)
    public void test() throws InterruptedException {
        final Metric metric = new Metric(12345L);

        producer.produce(metric);

        assertThat(consumer.take()).isEqualTo(metric);
    }
}
