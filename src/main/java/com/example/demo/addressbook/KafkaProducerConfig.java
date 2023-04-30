package com.example.demo.addressbook;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String kafkaUrl;

//    @Bean
//    Producer<String, Contact> getProducer() {
//        var configs = getConfigs();
//        return new KafkaProducer<>(configs);
//    }

    private HashMap<String, Object> getConfigs() {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ContactSerializer.class);
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        return configs;
    }

    @Bean
    public ProducerFactory<String, Contact> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigs());
    }

    @Bean
    public KafkaTemplate<String, Contact> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

