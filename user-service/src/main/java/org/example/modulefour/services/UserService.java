package org.example.modulefour.services;

import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.dto.UserDTO;
import org.example.modulefour.domain.entities.User;
import org.example.modulefour.domain.exceptions.DublicateEmailException;
import org.example.modulefour.domain.exceptions.UserNotFoundException;
import org.example.modulefour.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;

    KafkaProducerService kafkaProducerService;

    public UserService(UserRepository userRepository, KafkaProducerService kafkaProducerService) {
        this.userRepository = userRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public List<UserDTO> getAllUsers() {
        List<User> result = userRepository.findAll();
        return result.stream().map(i -> i.toDTO()).collect(Collectors.toList());
    }

    public UserDTO getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        return user.toDTO();
    }

    @Transactional
    public UserDTO createUser(UserCreateDTO userCreate) {
        if (userRepository.existsByEmail(userCreate.getEmail())) {
            throw new DublicateEmailException("Email: " + userCreate.getEmail() + " already exists");
        }
        User user = new User(userCreate);
        userRepository.save(user);
        kafkaProducerService.sendUserEvent("CREATE", user.getEmail());
        return user.toDTO();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public UserDTO deleteUser(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
        kafkaProducerService.sendUserEvent("DELETE", user.getEmail());
        return user.toDTO();
    }

    @Transactional
    public UserDTO updateUser(Long id, UserCreateDTO userUpdate) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(id);
        }
        if (userRepository.existsByEmail(userUpdate.getEmail())) {
            throw new DublicateEmailException("Email: " + userUpdate.getEmail() + " already exists");
        }
        user.setName(userUpdate.getName());
        user.setEmail(userUpdate.getEmail());
        user.setAge(userUpdate.getAge());
        userRepository.save(user);
        kafkaProducerService.sendUserEvent("UPDATE", user.getEmail());
        return user.toDTO();
    }
}
