package ru.skillbox.diplom.group25.microservice.streaming.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * KafkaTopicConfig
 *
 * @author alex90bar
 */

@Configuration
public class KafkaTopicConfig {

  @Value(value = "${spring.kafka.bootstrap-servers}")
  private String bootstrapAddress;
  @Value(value = "${kafka-topics.topic_test}")
  private String topicTest;
  @Value(value = "${kafka-topics.streaming_dialogs}")
  private String topicStreamingDialogs;
  @Value(value = "${kafka-topics.dialogs_streaming}")
  private String topicDialogsStreaming;

  @Bean
  public KafkaAdmin kafkaAdmin() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
    return new KafkaAdmin(configs);
  }

  @Bean
  public NewTopic topicTest() {
    return new NewTopic(topicTest, 1, (short) 1);
  }
  @Bean
  public NewTopic topicStreamingDialogs() {
    return new NewTopic(topicStreamingDialogs, 1, (short) 1);
  }
  @Bean
  public NewTopic topicDialogsStreaming() {
    return new NewTopic(topicDialogsStreaming, 1, (short) 1);
  }

}


