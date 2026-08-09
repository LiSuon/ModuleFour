package org.example.modulefour.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class UserCreateDTO {
    @Schema(description = "User's name", example = "John")
    @NotBlank(message = "Имя не может быть пустым")
    private String name;

    @Schema(description = "User's email", example = "John.email@email.ru")
    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @Schema(description = "User's age", example = "20")
    @Min(value = 1, message = "Возраст должен быть больше 0")
    private Integer age;

    public UserCreateDTO(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
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

}

