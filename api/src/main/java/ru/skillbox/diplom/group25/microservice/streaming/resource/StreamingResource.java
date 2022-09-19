package ru.skillbox.diplom.group25.microservice.streaming.resource;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.skillbox.diplom.group25.microservice.streaming.dto.StreamingDto;

/**
 * StreamingResource
 *
 * @author alex90bar
 */

@RequestMapping("api/v1/streaming/")
public interface StreamingResource {

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  void create(@RequestBody StreamingDto dto);

}


