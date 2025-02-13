package com.smarsh.queue_matcher_java.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smarsh.queue_matcher_java.domain.SurveillanceContext;
import com.smarsh.queue_matcher_java.domain.SurveillanceData;
import com.smarsh.queue_matcher_java.helpers.SurveillanceDataHelper;
import java.util.Arrays;
import org.apache.kafka.streams.KeyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PolicyExecutorTest {
  private SurveillanceContext surveillanceContext;
  private String gcid = "ef884e9d7f47a5fd1178e23cf9f7bb17";
  private PolicyExecutor policyExecutor;
  private final SurveillanceData surveillanceData = SurveillanceDataHelper.defaultBuilder("AAAA",
      "Test Mail", "internal bound");

  @BeforeEach
  void setUp() {
    policyExecutor = new PolicyExecutor();
    surveillanceContext = new SurveillanceContext(surveillanceData, Arrays.asList());
  }

  @Test
  void shouldExecutePoliciesForGivenIndexableJson() {
    KeyValue<String, SurveillanceContext> response = policyExecutor.apply(gcid,
        surveillanceContext);

    assertEquals(gcid, response.key);
    assertEquals(surveillanceContext, response.value);
  }
}