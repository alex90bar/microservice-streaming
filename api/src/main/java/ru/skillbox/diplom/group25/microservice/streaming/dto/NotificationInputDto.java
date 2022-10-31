package ru.skillbox.diplom.group25.microservice.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInputDto {
    private Long userId;
    private NotificationType notificationType;
    private String content;
}
