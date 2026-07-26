package org.example.modulefour.junittest;

import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.dto.UserDTO;
import org.example.modulefour.domain.entities.User;
import org.example.modulefour.domain.messages.ErrorsMessages;
import org.example.modulefour.repositories.UserRepository;
import org.example.modulefour.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User initializeUser(Integer i) {
        User user = new User("TestName" + i, "TestEmail" + i, 10);
        return user;
    }

    private UserCreateDTO initializeUserCreateDTO(Integer i) {
        UserCreateDTO user = new UserCreateDTO("TestName" + i, "TestEmail" + i, 10);
        return user;
    }

    @Test
    void getUserTest() {
        User user = initializeUser(0);
        Long id = user.getId();

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        ResponseEntity<?> result = userService.getUser(id);
        UserDTO resultUser = (UserDTO) result.getBody();

        assertNotNull(resultUser);
        assertEquals(user.getName(), resultUser.getName());
    }

    @Test
    void getUserTest_NotExists() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<?> result = userService.getUser(id);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getAllTest() {
        User user = initializeUser(0);
        User user2 = initializeUser(1);

        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(user.getName(), result.get(0).getName());
        assertEquals(user2.getName(), result.get(1).getName());
    }

    @Test
    void createUserTest() {
        User user = initializeUser(0);
        UserCreateDTO userDTO = initializeUserCreateDTO(0);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getEmail(), result.getEmail());
    }
}
