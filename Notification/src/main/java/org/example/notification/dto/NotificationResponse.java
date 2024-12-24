package org.example.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data

public class NotificationResponse {
    private Long id;
    private String message;
    private String status;
    private LocalDateTime dateEnvoi;

}