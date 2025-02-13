/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.serdes;

import java.util.List;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

public class CustomSerde {

  public <T> Serde<T> get(Class<T> clazz) {
    // Return a Serde using JsonSerializer and JsonDeserializer
    return Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(clazz));
  }

  public <T> Serde<List<T>> getList(Class<T> clazz) {
    // Return a Serde using JsonSerializer and JsonDeserializer
    return (Serde<List<T>>) Serdes.serdeFrom(new JsonSerializer<>(), new JsonDeserializer<>(clazz));
  }
}
