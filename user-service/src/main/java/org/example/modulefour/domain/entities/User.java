package org.example.modulefour.domain.entities;

import jakarta.persistence.*;
import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.dto.UserDTO;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private Integer age;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;

    public User() {}

    public User(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
        this.createdAt = LocalDate.now();
    }

    public User(UserCreateDTO userCreate) {
        this.name = userCreate.getName();
        this.email = userCreate.getEmail();
        this.age = userCreate.getAge();
        this.createdAt = LocalDate.now();
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
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public UserDTO toDTO() {
        return new UserDTO(name, email, age, createdAt);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", createdAt=" + createdAt +
                "}";
    }
}
