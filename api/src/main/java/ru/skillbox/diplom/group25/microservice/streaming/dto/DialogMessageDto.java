package ru.skillbox.diplom.group25.microservice.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DialogMessageDto
 *
 * @author alex90bar
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DialogMessageDto {

  private Long id;
  private Long authorId;
  private String messageText;
  private Long recipientId;
  private Long time;

}


