package ru.skillbox.diplom.group25.microservice.streaming.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.skillbox.diplom.group25.microservice.streaming.dto.StreamingDto;

/**
 * StreamingService
 *
 * @author alex90bar
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class StreamingService {

  private final KafkaService kafkaService;
  @Value(value = "${kafka-topics.topic_test}")
  private String topicTest;


  public void create(StreamingDto dto) {
    log.info("create begins with : {}", dto);
    kafkaService.sendMessage(topicTest, "new key", dto);
  }
}


