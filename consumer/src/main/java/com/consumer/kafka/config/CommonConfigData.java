package com.consumer.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class CommonConfigData {

    private String bootstrapServers;
    private String schemaRegistryUrl;
    private String schemaRegistryUrlKey;
}
