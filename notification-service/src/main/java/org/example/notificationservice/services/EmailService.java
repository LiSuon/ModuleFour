package org.example.notificationservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendNotification(String email, String operation) {
        String subject = "Уведомление об аккаунте";
        String text;
        if ("CREATE".equalsIgnoreCase(operation)) {
            text = "Здравствуйте! Ваш аккаунт на сайте был успешно создан.";
        } else if ("DELETE".equalsIgnoreCase(operation)) {
            text = "Здравствуйте! Ваш аккаунт был удалён.";
        } else {
            text = "Произошло изменение в вашем аккаунте.";
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
