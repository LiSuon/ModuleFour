package org.example.modulefour.mockmvctest;

import org.example.modulefour.controllers.UserController;
import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.entities.User;
import org.example.modulefour.initializers.PostgresInitializers;
import org.example.modulefour.repositories.UserRepository;
import org.example.modulefour.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureWebMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class UserControllerTest extends PostgresInitializers {
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private UserRepository userRepository;

    public UserCreateDTO initializeUserCreateDTO(Integer num) {
        UserCreateDTO dto = new UserCreateDTO("TestName" + num, "TestEmail" + num + "@email.com", 10 + num);
        return dto;
    }

    public User initializeUser(UserCreateDTO dto) {
        User user = new User(dto);
        return user;
    }


    @Test
    public void getUserTest() throws Exception {
        User user = initializeUser(initializeUserCreateDTO(0));
        userRepository.save(user);

        mockMvc.perform(get("/getUser/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));

    }

    @Test
    public void createUserTest() throws Exception {
        UserCreateDTO dto = initializeUserCreateDTO(1);

        mockMvc.perform(post("/createUser")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    public void deleteUserTest() throws Exception {
        User user = initializeUser(initializeUserCreateDTO(2));
        userRepository.save(user);

        mockMvc.perform(delete("/deleteUser/" + user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(user.getName()));
    }

    @Test
    public void updateUserTest() throws Exception {
        UserCreateDTO dto = initializeUserCreateDTO(3);
        User user = initializeUser(initializeUserCreateDTO(4));
        userRepository.save(user);

        mockMvc.perform(put("/updateUser/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(dto.getName()));
    }

    @Test
    public void getAllUsersTest() throws Exception{
        User user = initializeUser(initializeUserCreateDTO(5));
        User user2 = initializeUser(initializeUserCreateDTO(6));
        userRepository.saveAll(List.of(user, user2));

        mockMvc.perform(get("/allUsers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.customEntityModelList.length()").value(2))
                .andExpect(jsonPath("$._embedded.customEntityModelList[0].name").value(user.getName()))
                .andExpect(jsonPath("$._embedded.customEntityModelList[1].name").value(user2.getName()));
    }
}
