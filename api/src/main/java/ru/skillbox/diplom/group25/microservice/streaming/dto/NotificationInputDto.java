package ru.skillbox.diplom.group25.microservice.streaming.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationInputDto {
    private Long Id;
    private AuthorDto author;
    private NotificationType notificationType;
    private String content;
    private Long timestamp;
}
