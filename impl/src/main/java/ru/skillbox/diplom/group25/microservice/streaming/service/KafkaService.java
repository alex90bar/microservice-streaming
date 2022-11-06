package ru.skillbox.diplom.group25.microservice.streaming.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import ru.skillbox.diplom.group25.microservice.streaming.dto.DialogMessage;
import ru.skillbox.diplom.group25.microservice.streaming.dto.NotificationMessage;
import ru.skillbox.diplom.group25.microservice.streaming.utils.ContextUtils;

/**
 * KafkaService
 *
 * @author alex90bar
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaService {

  private final ContextUtils contextUtils;
  private final ObjectMapper objectMapper;


  @KafkaListener(topics = "${kafka-topics.topic_test}")
  public void listenTopicTest(ConsumerRecord<String, JsonNode> myRecord) {

    log.info("Получено сообщение в topic_test, key {} value {}", myRecord.key(), myRecord.value());

  }


  /**
   * Уведомление о получении сообщения и сохранения его в БД от microservice-dialog
   */
  @Async("taskExecutor")
  @KafkaListener(topics = "${kafka-topics.dialogs_streaming}")
  public void listenDialogsStreaming(ConsumerRecord<String, JsonNode> myRecord) throws IOException {

    log.info("Получено сообщение в топик dialogs_streaming, key {} value {}", myRecord.key(), myRecord.value());

    DialogMessage dialogMessage = mapJsonToObject(myRecord, DialogMessage.class);

    if (dialogMessage != null) {
      //Если получатель сообщения онлайн, отправляем сообщение ему в сокет
      if (contextUtils.contextContains(dialogMessage.getAccountId())) {
        contextUtils.getFromContext(dialogMessage.getAccountId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(dialogMessage)));
        log.info("UserId {} is online, sent to WebSocket: {}", dialogMessage.getAccountId(), dialogMessage);
      }
    }

  }

  @Async("taskExecutor")
  @KafkaListener(topics = "${kafka-topics.notifications_streaming}")
  public void listenNotificationsStreaming(ConsumerRecord<String, JsonNode> myRecord) throws IOException {

    log.info("Получено сообщение в топик notifications_streaming, key {} value {}", myRecord.key(), myRecord.value());

    NotificationMessage notificationMessage = mapJsonToObject(myRecord, NotificationMessage.class);

    if (notificationMessage != null) {
      if (contextUtils.contextContains(notificationMessage.getAccountId())) {
        contextUtils.getFromContext(notificationMessage.getAccountId())
            .sendMessage(new TextMessage(objectMapper.writeValueAsString(notificationMessage)));
        log.info("UserId {} is online, sent to WebSocket: {}", notificationMessage.getAccountId(), notificationMessage);
      }
    }
  }


  /**
   * Метод для преобразования входящего сообщения по kafka из формата Json в Object
   */
  private <T> T mapJsonToObject(ConsumerRecord<String, JsonNode> myRecord, Class<T> valueType) {

    try {
      return objectMapper.treeToValue(myRecord.value(), valueType);
    } catch (JsonProcessingException e) {
      log.error("Error reading message: {}", e.getMessage());
      return null;
    }
  }

}

