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
    private NotificationType type;
    private String content;
    private Long timeStamp;
}
