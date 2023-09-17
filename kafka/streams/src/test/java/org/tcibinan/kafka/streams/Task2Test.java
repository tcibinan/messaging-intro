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
import org.tcibinan.kafka.streams.config.Task2Configuration;

import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    @Test
    void test() {
        final Task2Configuration configuration = new Task2Configuration();

        final StreamsBuilder builder = new StreamsBuilder();
        configuration.task2Stream(builder);

        final Topology topology = builder.build();
        topology.addSink("words-short-topic-sink", "words-short-topic",
                Serdes.Integer().serializer(), Serdes.String().serializer(),
                "words-short");
        topology.addSink("words-long-topic-sink", "words-long-topic",
                Serdes.Integer().serializer(), Serdes.String().serializer(),
                "words-long");

        final Properties props = new Properties();
        props.putAll(Map.of(
                StreamsConfig.APPLICATION_ID_CONFIG, "test",
                StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "test:19092",
                StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName(),
                StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName()
        ));

        try (TopologyTestDriver testDriver = new TopologyTestDriver(topology, props)) {
            final TestInputTopic<String, String> sentencesInput = testDriver.createInputTopic(
                    configuration.task2Topic().name(),
                    Serdes.String().serializer(), Serdes.String().serializer());

            sentencesInput.pipeInput(null, "shortword verylongword");

            final TestOutputTopic<Integer, String> shortWordsOutput = testDriver.createOutputTopic(
                    "words-short-topic",
                    Serdes.Integer().deserializer(), Serdes.String().deserializer());
            final TestOutputTopic<Integer, String> longWordsOutput = testDriver.createOutputTopic(
                    "words-long-topic",
                    Serdes.Integer().deserializer(), Serdes.String().deserializer());

            assertThat(shortWordsOutput.readKeyValue()).isEqualTo(new KeyValue<>(9, "shortword"));
            assertThat(longWordsOutput.readKeyValue()).isEqualTo(new KeyValue<>(12, "verylongword"));
        }
    }
}
