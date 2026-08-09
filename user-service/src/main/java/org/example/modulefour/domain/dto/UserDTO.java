package org.example.modulefour.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public class UserDTO {
    @Schema(description = "User's id")
    private Long id;

    @Schema(description = "User's name", example = "John")
    private String name;

    @Schema(description = "User's email", example = "John.email@email.ru")
    private String email;

    @Schema(description = "User's age", example = "20")
    private Integer age;
    private LocalDate createdAt;

    public UserDTO(Long id, String name, String email, Integer age, LocalDate createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
