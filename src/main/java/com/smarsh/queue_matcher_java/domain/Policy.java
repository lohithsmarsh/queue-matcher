/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Policy (
  @JsonProperty(value = "_id") String id,
  String name,
  @JsonProperty(value = "supervisionPolicyType") PolicyType type,
  boolean isEchoEnabled,
  int version, @JsonProperty(value = "searchCriteria")
  SearchCriteria criteria,
  boolean isDeleted,
  boolean isDisabled
) {}
