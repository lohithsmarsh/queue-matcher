/*
 * Copyright 2025 Smarsh Inc.
 */

package com.smarsh.queue_matcher_java.topology;

import com.smarsh.queue_matcher_java.domain.Policy;
import com.smarsh.queue_matcher_java.domain.SurveillanceContext;
import com.smarsh.queue_matcher_java.domain.SurveillanceData;
import com.smarsh.queue_matcher_java.serdes.CustomSerde;
import com.smarsh.queue_matcher_java.service.PolicyExecutor;
import com.smarsh.queue_matcher_java.service.SurveillanceExecutor;
import java.util.Collections;
import java.util.List;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Joined;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class QueueMatcherProcessor {
  private static final Serde<String> STRING_SERDE = Serdes.String();
  private static final String CONDUCT_INGESTION_TOPIC = "conduct-ingestion-topic";
  private static final String OUTPUT_TOPIC = "sampling-topic";
  private static final String POLICIES_TOPIC = "policies-topic";

  public final CustomSerde customSerde = new CustomSerde();
  public final PolicyExecutor policyExecutor;
  public final SurveillanceExecutor surveillanceExecutor;

  @Autowired
  public QueueMatcherProcessor(final PolicyExecutor policyExecutor,
                               final SurveillanceExecutor surveillanceExecutor) {
    this.policyExecutor = policyExecutor;
    this.surveillanceExecutor = surveillanceExecutor;
  }

  @Autowired
  void buildPipeline(StreamsBuilder pipeline) {
    KStream<String, String> messageStream =
        pipeline.stream(CONDUCT_INGESTION_TOPIC, Consumed.with(STRING_SERDE,
            STRING_SERDE));
    Serde<List<Policy>> policySerde = customSerde.getList(Policy.class);
    KTable<String, List<Policy>> policiesTable = pipeline.table(POLICIES_TOPIC, Consumed.with(Serdes.String(),
        policySerde));
    KStream<String, SurveillanceData> data = messageStream
        .flatMapValues(value -> {
          SurveillanceData surveillanceData = customSerde.get(SurveillanceData.class).deserializer()
              .deserialize(CONDUCT_INGESTION_TOPIC, value.getBytes());
          return surveillanceData != null ? Collections.singletonList(surveillanceData) : Collections.emptyList();
        });

    KStream<String, SurveillanceContext> surveillanceContextStream = data
        .leftJoin(policiesTable,
            (surveillanceData, policies) -> {
              // If no policies found, use an empty list
              List<Policy> policyList = policies != null ? policies : Collections.emptyList();

              // Create a new SurveillanceContext object with SurveillanceData and policies
              return new SurveillanceContext(surveillanceData, policyList);
            },
            Joined.with(Serdes.String(), customSerde.get(SurveillanceData.class), policySerde)
        );

    surveillanceContextStream.map(policyExecutor::apply)
        .map(surveillanceExecutor::processRequest);

    data.to(OUTPUT_TOPIC, Produced.with(STRING_SERDE, customSerde.get(
        SurveillanceData.class)));
  }
}
