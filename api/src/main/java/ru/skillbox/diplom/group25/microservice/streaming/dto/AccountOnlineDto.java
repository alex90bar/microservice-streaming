package ru.skillbox.diplom.group25.microservice.streaming.dto;

import java.time.ZonedDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AccountOnlineDto
 *
 * @author alex90bar
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountOnlineDto {

  private Long id;
  private ZonedDateTime lastOnlineTime;
  private Boolean isOnline;

}


