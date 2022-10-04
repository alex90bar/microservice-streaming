package ru.skillbox.diplom.group25.microservice.streaming.websocket;

import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * SocketTextHandler
 *
 * @author alex90bar
 */

@Component
public class SocketTextHandler extends TextWebSocketHandler {

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws IOException {

    String payload = message.getPayload();
    session.sendMessage(new TextMessage("Received message: " + payload));
  }

}


