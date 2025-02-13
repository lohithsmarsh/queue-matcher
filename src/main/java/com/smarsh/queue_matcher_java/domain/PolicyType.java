/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.domain;

public enum PolicyType {
  FLAG("flag"),
  IGNORE("ignore");

  private final String type;

  PolicyType(String type) {
    this.type = type;
  }
}
