package org.example.notification.service;


import org.example.notification.dto.NotificationRequest;
import org.example.notification.dto.NotificationResponse;
import org.example.notification.model.Notification;
import org.example.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "ordre-topic", groupId = "notification-group")
    public void consumeOrdre(String ordreReference) {
        NotificationRequest request = new NotificationRequest();
        request.setMessage("Nouvel ordre reçu: " + ordreReference);
        request.setOrdreReference(ordreReference);
        createNotification(request);
    }

    public NotificationResponse createNotification(NotificationRequest request) {
        Notification notification = new Notification();
        notification.setMessage(request.getMessage());
        notification.setOrdreReference(request.getOrdreReference());
        notification.setStatus("SENT");
        notification.setDateEnvoi(LocalDateTime.now());

        notification = notificationRepository.save(notification);
        return convertToResponse(notification);
    }


    public NotificationResponse getNotification(Long id) {
        return notificationRepository.findById(id)
                .map(this::convertToResponse)
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + id));
    }

    private NotificationResponse convertToResponse(Notification notification) {
        NotificationResponse response = new NotificationResponse();
        response.setId(notification.getId());
        response.setMessage(notification.getMessage());
        response.setStatus(notification.getStatus());
        response.setDateEnvoi(notification.getDateEnvoi());
        return response;
    }

}