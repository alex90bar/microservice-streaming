package ru.skillbox.diplom.group25.microservice.streaming.resource;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.skillbox.diplom.group25.microservice.streaming.dto.StreamingDto;
import ru.skillbox.diplom.group25.microservice.streaming.service.StreamingService;

/**
 * StreamingResource
 *
 * @author alex90bar
 */

@RestController
@RequiredArgsConstructor
public class StreamingResourceImpl implements StreamingResource{

  private final StreamingService streamingService;

  @Override
  public void create(StreamingDto dto) {
    streamingService.create(dto);
  }
}


