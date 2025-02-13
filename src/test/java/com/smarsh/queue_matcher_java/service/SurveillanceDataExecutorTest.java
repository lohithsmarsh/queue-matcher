package com.smarsh.queue_matcher_java.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smarsh.queue_matcher_java.domain.SurveillanceContext;
import com.smarsh.queue_matcher_java.domain.SurveillanceData;
import com.smarsh.queue_matcher_java.helpers.SurveillanceDataHelper;
import java.util.Arrays;
import org.apache.kafka.streams.KeyValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SurveillanceDataExecutorTest
{
  private SurveillanceData surveillanceData;
  private SurveillanceContext context;
  private SurveillanceExecutor executor;
  private String gcid = "ef884e9d7f47a5fd1178e23cf9f7bb17";

  @BeforeEach
  void setUp() {
    executor = new SurveillanceExecutor();
    surveillanceData = SurveillanceDataHelper.defaultBuilder(gcid, "Test Mail", "internal bound");
    context = new SurveillanceContext(surveillanceData, Arrays.asList());
  }

  @Test
  void shouldExecuteAndGetTheResponse() {
    KeyValue<String, SurveillanceContext> response = executor.processRequest(gcid, context);

    assertEquals(gcid, response.key);
    assertEquals(context, response.value);
  }
}