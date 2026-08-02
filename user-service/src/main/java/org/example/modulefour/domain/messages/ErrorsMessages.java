package org.example.modulefour.domain.messages;

public enum ErrorsMessages {
    USER_NOT_FOUND("Пользователь не найден");

    private final String message;

    ErrorsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
