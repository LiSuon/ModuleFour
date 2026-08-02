package org.example.modulefour.services;

import org.example.modulefour.domain.dto.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String TOPIC = "user-events";

    @Autowired
    private KafkaTemplate<String, UserEvent> kafkaTemplate;

    public void sendUserEvent(String operation, String email) {
        UserEvent event = new UserEvent(email, operation);
        kafkaTemplate.send(TOPIC, event);
    }
}
