package ru.skillbox.diplom.group25.microservice.streaming.service;

import com.fasterxml.jackson.databind.JsonNode;
import java.util.Arrays;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * KafkaService
 *
 * @author alex90bar
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {



  @KafkaListener(topics = "${kafka-topics.topic_test}")
  public void listenTopicTest(ConsumerRecord<String, JsonNode> myRecord){

    log.info("Получено сообщение в topic_test, key {} value {}", myRecord.key(), myRecord.value());


  }


}


