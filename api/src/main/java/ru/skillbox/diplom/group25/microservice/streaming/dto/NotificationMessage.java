package ru.skillbox.diplom.group25.microservice.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationMessage {
    private String type = "NOTIFICATION";
    private Long accountId;
    private NotificationMessageDto data;
}
