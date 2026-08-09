package org.example.notificationservice.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.example.notificationservice.domain.dto.UserEvent;
import org.example.notificationservice.services.EmailService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class UserEventListener {
    private static final Logger logger = LoggerFactory.getLogger(UserEventListener.class);

    @Autowired
    private EmailService emailService;

    @KafkaListener(topics = "user-events", groupId = "notification-group")
    public void handleUserEvent(String message) {
        try {
//            ConsumerRecord
            ObjectMapper mapper = new ObjectMapper(); // Jackson 3
            UserEvent event = mapper.readValue(message, UserEvent.class);
            emailService.sendNotification(event.getEmail(), event.getOperation());
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }
}