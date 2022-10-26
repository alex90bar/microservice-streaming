package ru.skillbox.diplom.group25.microservice.streaming.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import ru.skillbox.diplom.group25.microservice.streaming.websocket.SocketTextHandler;

/**
 * WebSocketConfig
 *
 * @author alex90bar
 */

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

  private final SocketTextHandler socketTextHandler;

  @Value(value = "${socket_path}")
  private String socketPath;

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(socketTextHandler, socketPath).setAllowedOriginPatterns("*");
  }

}


