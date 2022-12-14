package ru.skillbox.diplom.group25.microservice.streaming.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jwt.JWTClaimsSet;
import java.io.IOException;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.skillbox.diplom.group25.microservice.streaming.dto.AccountOnlineDto;
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
  @Value(value = "${kafka-topics.streaming_account}")
  private String topicStreamingAccount;

  /**
   * Метод для извлечения userId текущего пользователя из jwt-токена
   * */
  private Long getCurrentUserIdFromJwt(String token){

    log.info("Token: {}", token);
    token = token.substring(7);

    JWSObject jwsObject;
    JWTClaimsSet claims = null;

    try {
      jwsObject = JWSObject.parse(token);
      claims =  JWTClaimsSet.parse(jwsObject.getPayload().toJSONObject());
    } catch (java.text.ParseException e) {
      log.error("Error jwt parsing: {}", e.getMessage());

    }

    Long id = (Long) claims.getClaim("id");

    log.info("current userId id from jwt from WebSocketSession Headers: {}", id);

    return id;

  }

  /**
   * При установке соединения добавляем текущего пользователя в context для отслеживания его состояния "онлайн"
   * */
  @Override
  public void afterConnectionEstablished(WebSocketSession session) {

    String token = session.getHandshakeHeaders().get("Authorization").get(0);

    Long userId = getCurrentUserIdFromJwt(token);

    log.info("Connection established with userId: {}", userId);

    AccountOnlineDto accountOnlineDto = new AccountOnlineDto(userId, null, true);

    //отправляем сообщение в аккаунт-сервис для смены статуса пользователя на "онлайн"
    kafkaSender.sendMessage(topicStreamingAccount, null, accountOnlineDto);

    contextUtils.putToContext(userId, session);

  }

  /**
   * При разрыве соединения удаляем текущего пользователя из context, теперь он не "онлайн"
   * */
  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

    String token = session.getHandshakeHeaders().get("Authorization").get(0);

    Long userId = getCurrentUserIdFromJwt(token);

    log.info("Connection closed with userId: {}", userId);

    AccountOnlineDto accountOnlineDto = new AccountOnlineDto(userId, ZonedDateTime.now(), false);

    //отправляем сообщение в аккаунт-сервис для смены статуса пользователя на "офлайн", фиксируем дату выхода из онлайн
    //для отображения на фронте поля с датой "последний раз был в..."
    kafkaSender.sendMessage(topicStreamingAccount, null, accountOnlineDto);

    contextUtils.removeFromContext(userId);
  }

  /**
   * Метод обработки входящего сообщения
   * */
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

  }

}


