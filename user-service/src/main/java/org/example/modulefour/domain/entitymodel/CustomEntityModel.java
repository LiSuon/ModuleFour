package org.example.modulefour.domain.entitymodel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.example.modulefour.domain.dto.UserDTO;
import org.springframework.hateoas.Link;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonPropertyOrder({"id", "name", "email", "age", "createdAt", "_links"})
public class CustomEntityModel {
    private Long id;
    private String name;
    private String email;
    private Integer age;
    private LocalDate createdAt;

    @JsonProperty("_links")
    private List<Link> links = new ArrayList<>();

    public CustomEntityModel(UserDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.email = dto.getEmail();
        this.age = dto.getAge();
        this.createdAt = dto.getCreatedAt();
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

    public List<Link> getLinks() {
        return links;
    }

    public void addLink(Link link) {
        links.add(link);
    }

    public void addLinks(List<Link> links) {
        this.links.addAll(links);
    }
}
