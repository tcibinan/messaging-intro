package org.tcibinan.kafka.streams.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.StreamJoined;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.time.Duration;

@Configuration
public class Task3Configuration {

    @Bean
    public NewTopic task31Topic() {
        return TopicBuilder.name("task-3-1")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic task32Topic() {
        return TopicBuilder.name("task-3-2")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Autowired
    public void task3Stream(final StreamsBuilder streamsBuilder) {
        final KStream<String, String> stream1 = streamsBuilder.stream("task-3-1");
        final KStream<String, String> stream2 = streamsBuilder.stream("task-3-2");
        join(process(stream1), process(stream2));
    }

    private KStream<Long, String> process(final KStream<String, String> stream) {
        final KStream<Long, String> processed = stream
                .filter(((key, value) -> value != null && value.contains(":")))
                .map((key, value) -> {
                    final String[] items = value.split(":", 2);
                    return new KeyValue<>(Long.valueOf(items[0]), items[1]);
                });
        processed.print(Printed.toSysOut());
        return processed;
    }

    private void join(final KStream<Long, String> left, final KStream<Long, String> right) {
        left.join(right,
                        (k, v) -> k + " " + v,
                        JoinWindows.ofTimeDifferenceAndGrace(Duration.ofMinutes(1), Duration.ofSeconds(30)),
                        StreamJoined.with(Serdes.Long(), Serdes.String(), Serdes.String()))
                .print(Printed.toSysOut());
    }
}
