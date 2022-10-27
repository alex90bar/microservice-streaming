package ru.skillbox.diplom.group25.microservice.streaming.utils;

import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

/**
 * ContextUtils
 *
 * @author alex90bar
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ContextUtils {

  private static ConcurrentHashMap<Long, WebSocketSession> context = new ConcurrentHashMap<>();


  public void putToContext(Long id, WebSocketSession session) {
    context.put(id, session);
    log.info("put to context id: {}", id);
  }

  public WebSocketSession getFromContext(Long id){
    return context.get(id);
  }

  public boolean contextContains(Long id) {
    return context.containsKey(id);
  }

  public void removeFromContext(Long id) {
    context.remove(id);
    log.info("remove from context: {}", id);
  }

}


