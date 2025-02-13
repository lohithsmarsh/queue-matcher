/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.service;

import com.smarsh.queue_matcher_java.domain.SurveillanceContext;
import org.apache.kafka.streams.KeyValue;
import org.springframework.stereotype.Service;

@Service
public class PolicyExecutor {

  public KeyValue<String, SurveillanceContext> apply(String key, SurveillanceContext context) {
    return KeyValue.pair(key, context);
  }
}
