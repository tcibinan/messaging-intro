package org.tcibinan.kafka.streams.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Named;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.tcibinan.kafka.streams.message.Employee;

@Configuration
public class Task4Configuration {

    @Bean
    public NewTopic task4Topic() {
        return TopicBuilder.name("task-4")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Autowired
    public void task4Stream(final StreamsBuilder streamsBuilder) {
        final KStream<String, Employee> stream = streamsBuilder.stream("task-4",
                Consumed.with(Serdes.String(), new JsonSerde<>(Employee.class)))
                        .filter((key, value) -> value != null, Named.as("employees"));
        stream.print(Printed.toSysOut());
    }
}
