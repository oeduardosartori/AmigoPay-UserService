package com.amigopay.user.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic userCreateTopic() {
        return TopicBuilder
                .name("user.created")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
