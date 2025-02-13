/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.domain;

import java.util.List;

public record SurveillanceContext (
  SurveillanceData surveillanceData,
  List<Policy> policies
) {}
