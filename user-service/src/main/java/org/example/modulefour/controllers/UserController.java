package org.example.modulefour.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.modulefour.domain.dto.UserCreateDTO;
import org.example.modulefour.domain.dto.UserDTO;
import org.example.modulefour.domain.entitymodel.CustomEntityModel;
import org.example.modulefour.domain.entitymodel.UserModelAssembler;
import org.example.modulefour.services.UserService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "User Management", description = "Endpoints for managing users")
@RequestMapping("/api/users")
public class UserController {
    private UserService userService;
    private UserModelAssembler assembler;

    public UserController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService;
        this.assembler = assembler;
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved")
    })
    @GetMapping("/allUsers")
    public ResponseEntity<CollectionModel<CustomEntityModel>> getAllUsers() {
        List<UserDTO> result = userService.getAllUsers();
        CollectionModel<CustomEntityModel> collectionModel = assembler.toCollectionModel(result);
        return ResponseEntity.ok(collectionModel);
    }

    @Operation(summary = "Get user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUser(id);
        CustomEntityModel model = assembler.toModel(user, true);
        return ResponseEntity.ok(model);
    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Duplicated data")
    })
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateDTO dto) {
        UserDTO user = userService.createUser(dto);
        CustomEntityModel model = assembler.toModel(user, true);
        return ResponseEntity.status(HttpStatus.CREATED).body(model);
    }

    @Operation(summary = "Delete user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        UserDTO user = userService.deleteUser(id);
        CustomEntityModel model = assembler.toModel(user, true);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }

    @Operation(summary = "Update an existing user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "Duplicated data")
    })
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserCreateDTO dto) {
        UserDTO user = userService.updateUser(id, dto);
        CustomEntityModel model = assembler.toModel(user, true);
        return ResponseEntity.status(HttpStatus.OK).body(model);
    }
}
