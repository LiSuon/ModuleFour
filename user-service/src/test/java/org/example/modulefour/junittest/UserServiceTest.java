package org.example.modulefour.junittest;

import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.dto.UserDTO;
import org.example.modulefour.domain.entities.User;
import org.example.modulefour.domain.exceptions.UserNotFoundException;
import org.example.modulefour.domain.messages.ErrorsMessages;
import org.example.modulefour.repositories.UserRepository;
import org.example.modulefour.services.KafkaProducerService;
import org.example.modulefour.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

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

        UserDTO result = userService.getUser(id);

        assertNotNull(result);
        assertEquals(user.getName(), result.getName());
        verify(userRepository).findById(id);
    }

    @Test
    void getUserTestNotExists() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUser(id)
        );

        assertEquals("User with id 1 not found", exception.getMessage());
    }

    @Test
    void getAllTest() {
        User user = initializeUser(0);
        User user2 = initializeUser(1);

        when(userRepository.findAll()).thenReturn(List.of(user, user2));

        List<UserDTO> result = userService.getAllUsers();

        assertEquals(user.getName(), result.get(0).getName());
        assertEquals(user2.getName(), result.get(1).getName());
        verify(userRepository).findAll();

    }

    @Test
    void createUserTest() {
        User user = initializeUser(0);
        UserCreateDTO userDTO = initializeUserCreateDTO(0);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.createUser(userDTO);

        assertEquals(userDTO.getName(), result.getName());
        assertEquals(userDTO.getEmail(), result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUserTest() {
        User user = initializeUser(0);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserDTO resultDTO = userService.deleteUser(user.getId());

        assertEquals(user.getName(), resultDTO.getName());
        verify(userRepository).findById(user.getId());
        verify(userRepository).deleteById(user.getId());
    }

    @Test
    void deleteUserTestNotExists() {
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUser(id)
        );

        assertEquals("User with id 99 not found", exception.getMessage());
    }

    @Test
    void updateUserTest() {
        User user = initializeUser(0);
        Long id = user.getId();
        UserCreateDTO userCreateDTO = initializeUserCreateDTO(1);
        User updatedUser = initializeUser(1);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        UserDTO body = userService.updateUser(id, userCreateDTO);

        assertEquals(user.getName(), body.getName());
        assertEquals(user.getEmail(), body.getEmail());
        verify(userRepository).findById(id);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updateUserTestNotExists() {
        Long id = 99L;
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.getUser(id)
        );

        assertEquals("User with id 99 not found", exception.getMessage());

    }
}
