package com.coffzin.controller;

import com.coffzin.dto.request.UserRequestDTO;
import com.coffzin.dto.response.UserResponseDTO;
import com.coffzin.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid data supplied")
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(
            @RequestBody UserRequestDTO request
    ) {
        return ResponseEntity.ok(userService.create(request));
    }

    @GetMapping
    public List<UserResponseDTO> getAllUsers() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.getByEmail(email));
    }

    @Operation(summary = "Update a user by ID")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDTO request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }

    @Operation(summary = "Delete a user by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
