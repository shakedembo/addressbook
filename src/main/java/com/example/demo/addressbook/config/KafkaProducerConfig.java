package com.example.demo.addressbook.config;

import com.example.demo.addressbook.Contact;
import com.example.demo.addressbook.ContactSerializer;
import com.example.demo.addressbook.KafkaMessage;
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

    private HashMap<String, Object> getConfigs() {
        var configs = new HashMap<String, Object>();
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ContactSerializer.class);
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaUrl);
        return configs;
    }

    @Bean
    public ProducerFactory<String, KafkaMessage<Contact>> producerFactory() {
        return new DefaultKafkaProducerFactory<>(getConfigs());
    }

    @Bean
    public KafkaTemplate<String, KafkaMessage<Contact>> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}

