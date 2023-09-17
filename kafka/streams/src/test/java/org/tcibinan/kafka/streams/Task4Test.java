package org.tcibinan.kafka.streams;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.tcibinan.kafka.streams.config.Task2Configuration;
import org.tcibinan.kafka.streams.config.Task4Configuration;
import org.tcibinan.kafka.streams.message.Employee;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class Task4Test {
    @Test
    void test() {
        final Task4Configuration configuration = new Task4Configuration();

        final StreamsBuilder builder = new StreamsBuilder();
        configuration.task4Stream(builder);

        final Topology topology = builder.build();
        topology.addSink("employees-topic-sink", "employees-topic",
                Serdes.String().serializer(), new JsonSerde<>(Employee.class).serializer(),
                "employees");

        final Properties props = new Properties();
        props.putAll(Map.of(
                StreamsConfig.APPLICATION_ID_CONFIG, "test",
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "test:19092",
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName()
        ));

        try (TopologyTestDriver testDriver = new TopologyTestDriver(topology, props)) {
            final TestInputTopic<String, Employee> employeesInput = testDriver.createInputTopic(
                    configuration.task4Topic().name(),
                    Serdes.String().serializer(),
                    new JsonSerde<>(Employee.class).serializer());

            final Employee employee = new Employee("name", "company", "position", 5);
            employeesInput.pipeInput(null, employee);

            final TestOutputTopic<String, Employee> employeesOutput = testDriver.createOutputTopic(
                    "employees-topic",
                    Serdes.String().deserializer(),
                    new JsonSerde<>(Employee.class).deserializer());

            assertThat(employeesOutput.readKeyValue()).isEqualTo(new KeyValue<>(null, employee));
        }
    }
}
