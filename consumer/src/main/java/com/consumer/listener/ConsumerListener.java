package com.consumer.listener;

import com.example.ClientAvro;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ConsumerListener {

    @KafkaListener(id = "group-id", topics = "clients-topic")
    public void receive(@Payload List<ClientAvro> messages,
            @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        log.info("{} number of payment responses received with keys:{}, partitions:{}, offsets: {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());
    }

}
