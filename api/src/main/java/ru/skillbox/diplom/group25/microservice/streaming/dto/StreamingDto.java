package ru.skillbox.diplom.group25.microservice.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * StreamingDto
 *
 * @author alex90bar
 */

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class StreamingDto {

  private String name;
  private Integer count;

}


