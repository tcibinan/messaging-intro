package org.tcibinan.kafka.streams.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Task1Configuration {

    @Bean
    public NewTopic task11Topic() {
        return TopicBuilder.name("task-1-1")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic task12Topic() {
        return TopicBuilder.name("task-1-2")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Autowired
    public void task1Stream(final StreamsBuilder streamsBuilder) {
        final KStream<String, String> stream = streamsBuilder.stream("task-1-1");
        process(stream);
    }

    private void process(final KStream<String, String> stream) {
        stream.print(Printed.toSysOut());
        stream.to("task-1-2");
    }
}
