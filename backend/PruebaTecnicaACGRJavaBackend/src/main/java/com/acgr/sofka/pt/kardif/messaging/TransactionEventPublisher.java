package com.acgr.sofka.pt.kardif.messaging;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

/**
 * Publishes {@link TransactionEvent} instances to Kafka without blocking the calling flow.
 */
@Component
public class TransactionEventPublisher {

    private final KafkaTemplate<String, TransactionEvent> kafkaTemplate;
    private final String topic;

    public TransactionEventPublisher(KafkaTemplate<String, TransactionEvent> kafkaTemplate,
            @Value("${kardif.kafka.topic.transactions}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    public Mono<Void> publish(TransactionEvent event) {
        return Mono.create(sink -> kafkaTemplate.send(topic, event).addCallback(
                result -> sink.success(),
                sink::error));
    }
}
