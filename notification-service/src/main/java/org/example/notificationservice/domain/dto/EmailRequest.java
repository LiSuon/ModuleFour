package org.example.notificationservice.domain.dto;

public class EmailRequest {
    private String email;
    private String operation;

    public EmailRequest() {}

    public EmailRequest(String email, String operation) {
        this.email = email;
        this.operation = operation;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getOperation() { return operation; }
    public void setOperation(String operation) { this.operation = operation; }
}
