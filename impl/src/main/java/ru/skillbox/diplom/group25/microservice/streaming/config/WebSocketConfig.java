package ru.skillbox.diplom.group25.microservice.streaming.config;

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
public class WebSocketConfig implements WebSocketConfigurer {

  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry.addHandler(new SocketTextHandler(), "api/v1/streaming/ws");
  }

}


