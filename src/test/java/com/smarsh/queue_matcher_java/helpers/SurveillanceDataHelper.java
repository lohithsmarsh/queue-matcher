/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.helpers;

import com.smarsh.queue_matcher_java.domain.SurveillanceData;
import java.util.Objects;

public class SurveillanceDataHelper {

  public static SurveillanceData defaultBuilder(final String gcid, final String subject,
                                                final String communicationType) {
    return new SurveillanceData(gcid, subject, communicationType);
  }
}
