/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.config;

import static org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

@Configuration
@EnableKafka
@EnableKafkaStreams
public class KafkaConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapServers;

  @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
  KafkaStreamsConfiguration kafkaStreamsConfiguration() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(APPLICATION_ID_CONFIG, "queue-matcher-app");
    configs.put(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
    configs.put(DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
    configs.put(DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());

    return new KafkaStreamsConfiguration(configs);
  }
}
