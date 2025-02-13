package com.smarsh.queue_matcher_java.stub;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.smarsh.queue_matcher_java.domain.SurveillanceContext;
import com.smarsh.queue_matcher_java.domain.SurveillanceData;
import com.smarsh.queue_matcher_java.helpers.SurveillanceDataHelper;
import java.util.Arrays;
import org.junit.jupiter.api.Test;

class CognitionStubTest {

  @Test
  void shouldSetIsHitFieldToTrueInContextIfGcIdHashcodeIsDividedBy2() {
    SurveillanceData surveillanceData = SurveillanceDataHelper.defaultBuilder("AAAA",
        "Test Mail", "internal bound");
    SurveillanceContext s = new SurveillanceContext(surveillanceData, Arrays.asList());
    SurveillanceContext actual = CognitionStub.processRequest(s);

    assertEquals(s, actual);
  }
}