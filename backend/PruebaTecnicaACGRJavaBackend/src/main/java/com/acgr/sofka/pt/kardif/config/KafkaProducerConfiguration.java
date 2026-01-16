package com.acgr.sofka.pt.kardif.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.acgr.sofka.pt.kardif.messaging.TransactionEvent;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Configures the Kafka producer factory and template so transaction events can be published asynchronously.
 */
@Configuration
@ConditionalOnProperty(prefix = "kardif.kafka", name = "enabled", havingValue = "true", matchIfMissing = false)
public class KafkaProducerConfiguration {

    private final KafkaProperties kafkaProperties;
    private final ObjectMapper objectMapper;

    public KafkaProducerConfiguration(KafkaProperties kafkaProperties, ObjectMapper objectMapper) {
        this.kafkaProperties = kafkaProperties;
        this.objectMapper = objectMapper;
    }

    @Bean
    public ProducerFactory<String, TransactionEvent> transactionEventProducerFactory() {
        Map<String, Object> configs = new HashMap<>(kafkaProperties.buildProducerProperties());
        configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        JsonSerializer<TransactionEvent> serializer = new JsonSerializer<>(objectMapper);
        serializer.setAddTypeInfo(false);
        return new DefaultKafkaProducerFactory<>(configs, new StringSerializer(), serializer);
    }

    @Bean
    public KafkaTemplate<String, TransactionEvent> transactionEventKafkaTemplate(
            ProducerFactory<String, TransactionEvent> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
