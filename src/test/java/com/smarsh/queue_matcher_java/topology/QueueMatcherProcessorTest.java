package com.smarsh.queue_matcher_java.topology;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarsh.queue_matcher_java.domain.SurveillanceData;
import com.smarsh.queue_matcher_java.serdes.CustomSerde;
import com.smarsh.queue_matcher_java.service.PolicyExecutor;
import com.smarsh.queue_matcher_java.service.SurveillanceExecutor;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.TestOutputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.jupiter.api.Test;

class QueueMatcherProcessorTest {

  private static final String OUTPUT_TOPIC = "sampling-topic";
  private static final String CONDUCT_INGESTION_TOPIC = "conduct-ingestion-topic";

  QueueMatcherProcessor processor = new QueueMatcherProcessor(
      new PolicyExecutor(), new SurveillanceExecutor());

  InputStream inputStream = getClass().getClassLoader().getResourceAsStream("index_json.json");

  @Test
  void shouldBeAbleToBuildPipelineAndExecuteTheIndexableJson() throws IOException {
    StreamsBuilder streamsBuilder = new StreamsBuilder();

    processor.buildPipeline(streamsBuilder);

    Topology topology = streamsBuilder.build();

    try (TopologyTestDriver topologyTestDriver = new TopologyTestDriver(topology, new Properties())) {
      TestInputTopic<String, String> inputTopic = topologyTestDriver
          .createInputTopic(CONDUCT_INGESTION_TOPIC, new StringSerializer(), new StringSerializer());


      TestOutputTopic<String, SurveillanceData> outputTopic = topologyTestDriver
          .createOutputTopic(OUTPUT_TOPIC, new StringDeserializer(), new CustomSerde().get(
              SurveillanceData.class).deserializer());
      String indexableJson =  new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
      inputTopic.pipeInput("key", indexableJson);
      inputTopic.pipeInput("key2", indexableJson);

      SurveillanceData
          surveillanceData = new ObjectMapper().readValue(indexableJson, SurveillanceData.class);
      assertThat(outputTopic.readKeyValuesToList())
          .contains(
              KeyValue.pair("key", surveillanceData),
              KeyValue.pair("key2", surveillanceData)
          );

    }
  }
}