package ru.skillbox.diplom.group25.microservice.streaming.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.skillbox.diplom.group25.microservice.streaming.dto.DialogMessage;
import ru.skillbox.diplom.group25.microservice.streaming.service.KafkaSender;

/**
 * SocketTextHandler
 *
 * @author alex90bar
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class SocketTextHandler extends TextWebSocketHandler {

  private final ObjectMapper objectMapper;
  private final KafkaSender kafkaSender;

  @Value(value = "${kafka-topics.streaming_dialogs}")
  private String topicStreamingDialogs;

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws IOException {

    String payload = message.getPayload();
    log.info("Received message: {}", payload);

    JsonNode jsonNode = objectMapper.readTree(payload);
    String type = jsonNode.get("type").textValue();

    //Если поле "type" = "MESSAGE", отправляем собщение в microservice-dialog
    if (type.equals("MESSAGE")){

      DialogMessage dialogMessage = objectMapper.readValue(payload, DialogMessage.class);
      kafkaSender.sendMessage(topicStreamingDialogs, null, dialogMessage);

    }




//    session.sendMessage(new TextMessage("Received message: " + payload));
  }

}


