package org.example.modulefour.services;

import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.dto.UserDTO;
import org.example.modulefour.domain.entities.User;
import org.example.modulefour.domain.messages.ErrorsMessages;
import org.example.modulefour.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAllUsers() {
        List<User> result = userRepository.findAll();
        return result.stream().map(i -> i.toDTO()).collect(Collectors.toList());
    }

    public ResponseEntity<?> getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ErrorsMessages.USER_NOT_FOUND.getMessage()));
        }
        return ResponseEntity.ok(user.toDTO());
    }

    public UserDTO createUser(UserCreateDTO userCreate) {
        User user = new User(userCreate);
        userRepository.save(user);
        return user.toDTO();
    }

    public ResponseEntity<?> deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ErrorsMessages.USER_NOT_FOUND.getMessage()));
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok(user.toDTO());
    }

    public ResponseEntity<?> updateUser(Long id, UserCreateDTO userUpdate) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ErrorsMessages.USER_NOT_FOUND.getMessage()));
        }
        user.setName(userUpdate.getName());
        user.setEmail(userUpdate.getEmail());
        user.setAge(userUpdate.getAge());
        userRepository.save(user);
        return ResponseEntity.ok(user.toDTO());
    }
}
