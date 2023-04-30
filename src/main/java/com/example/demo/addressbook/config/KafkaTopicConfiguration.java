package com.example.demo.addressbook.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfiguration {
    @Value(value = "${application.kafka.topic}")
    private String topicName;

    @Bean
    public NewTopic topicExample() {
        return TopicBuilder.name(topicName).build();
    }
}
