package com.example.kafka.service.impl;

import com.example.kafka.service.KafkaProducer;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message) {
        log.info("Sending message={} to topic={}", message, topicName);
        CompletableFuture<SendResult<K, V>> kafkaResultFuture = kafkaTemplate.send(topicName, key, message);
        log.info("Message sent ={} to topic={}", message, topicName);
        kafkaResultFuture.whenComplete((kvSendResult, throwable) -> {
            if (kvSendResult != null) {
                RecordMetadata metadata = kvSendResult.getRecordMetadata();
                log.info(
                        "Received successful response from Kafka for order id: {}"
                                + " Topic: {} Partition: {} Offset: {} Timestamp: {}",
                        metadata.topic(), metadata.partition(), metadata.offset(), metadata.timestamp());
            } else if (throwable != null) {
                log.error("An error has occured: {}", throwable.getMessage());
            }
        });
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            log.info("Closing kafka producer!");
            kafkaTemplate.destroy();
        }
    }
}
