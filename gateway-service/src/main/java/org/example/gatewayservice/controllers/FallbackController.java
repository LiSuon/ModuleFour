package org.example.gatewayservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
public class FallbackController {
    @GetMapping("/fallback/users")
    public Mono<Map<String, String>> usersFallback() {
        return Mono.just(Map.of(
                "status", "503",
                "message", "Сервис пользователей временно недоступен. Попробуйте позже."
        ));
    }

    @GetMapping("/fallback/notifications")
    public Mono<Map<String, String>> notificationsFallback() {
        return Mono.just(Map.of(
                "status", "503",
                "message", "Сервис уведомлений временно недоступен. Попробуйте позже."
        ));
    }

}
