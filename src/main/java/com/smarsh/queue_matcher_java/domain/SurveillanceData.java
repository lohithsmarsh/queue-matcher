/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SurveillanceData(
    String gcid,
    @JsonProperty(value = "commtype") String communicationType,
    @JsonProperty(value = "subj") String subject
) {}
