package org.tcibinan.kafka.streams.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.Arrays;
import java.util.Map;

@Configuration
public class Task2Configuration {

    @Bean
    public NewTopic task2Topic() {
        return TopicBuilder.name("task-2")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Autowired
    public void task2Stream(final StreamsBuilder streamsBuilder) {
        final KStream<String, String> stream = streamsBuilder.stream("task-2");
        merge(split(stream));
    }

    private Map<String, KStream<Integer, String>> split(final KStream<String, String> sentences) {
        final KStream<Integer, String> words = sentences
                .filter((key, value) -> value != null)
                .flatMap((key, value) -> Arrays.stream(value.split("\\W"))
                        .map(word -> new KeyValue<>(word.length(), word))
                        .toList());
        words.print(Printed.toSysOut());
        return words
                .split(Named.as("words-"))
                .branch(((key, value) -> key < 10), Branched.as("short"))
                .defaultBranch(Branched.as("long"));
    }

    public void merge(final Map<String, KStream<Integer, String>> words) {
        words.values().stream()
                .map(it -> it.filter((key, value) -> value.contains("a")))
                .reduce(KStream::merge)
                .orElseThrow()
                .print(Printed.toSysOut());
    }
}
