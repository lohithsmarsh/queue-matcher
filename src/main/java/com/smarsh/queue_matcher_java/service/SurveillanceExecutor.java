/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.service;

import com.smarsh.queue_matcher_java.domain.SurveillanceContext;
import com.smarsh.queue_matcher_java.stub.CognitionStub;
import org.apache.kafka.streams.KeyValue;
import org.springframework.stereotype.Service;

@Service
public class SurveillanceExecutor {

  public KeyValue<String, SurveillanceContext> processRequest(final String key,
                                                              final SurveillanceContext context) {
    SurveillanceContext updatedContext = CognitionStub.processRequest(context);
    return KeyValue.pair(key, updatedContext);
  }
}
