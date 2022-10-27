package ru.skillbox.diplom.group25.microservice.streaming.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.skillbox.diplom.group25.library.core.util.TokenUtil;
import ru.skillbox.diplom.group25.microservice.streaming.dto.DialogMessage;
import ru.skillbox.diplom.group25.microservice.streaming.service.KafkaSender;
import ru.skillbox.diplom.group25.microservice.streaming.utils.ContextUtils;

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
  private final ContextUtils contextUtils;

  @Value(value = "${kafka-topics.streaming_dialogs}")
  private String topicStreamingDialogs;

  /**
   * При установке соединения добавляем текущего пользователя в context для отслеживания его состояния "онлайн"
   * */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    Thread.sleep(1000);

    Long userId = TokenUtil.getJwtInfo().getId();

    log.info("Connection established with userId: {}", userId);

    contextUtils.putToContext(userId, session);

  }

  /**
   * При разрыве соединения удаляем текущего пользователя из context, теперь он не "онлайн"
   * */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

    Long userId = TokenUtil.getJwtInfo().getId();

    log.info("Connection closed with userId: {}", userId);

    contextUtils.removeFromContext(userId);
  }

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

      //Если получатель сообщения онлайн, отправляем сообщение ему в сокет
      if (contextUtils.contextContains(dialogMessage.getAccountId())){
        contextUtils.getFromContext(dialogMessage.getAccountId()).sendMessage(new TextMessage(objectMapper.writeValueAsString(dialogMessage)));
        log.info("sent to WebSocket: {}", dialogMessage);
      }

    }




//    session.sendMessage(new TextMessage("Received message: " + payload));
  }

}


