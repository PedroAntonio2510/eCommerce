package io.github.api.controller;

import io.github.api.domain.User;
import io.github.api.domain.dto.UserRequestDTO;
import io.github.api.domain.mapper.UserModelMapper;
import io.github.api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController implements GenericController {

    private final UserService service;
    private final UserModelMapper userModelMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserRequestDTO request) {
        User user = userModelMapper.toEntity(request);
        URI uri = headerLocation(user.getId());
        service.createUser(user);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/manager")
    public ResponseEntity<?> saveUserManager(@RequestBody @Valid UserRequestDTO request) {
        User user = userModelMapper.toEntity(request);
        URI uri = headerLocation(user.getId());
        service.createAdminUser(user);
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<User> userFound = service.getById(id);
        service.deleteUser(userFound.get().getId());
        return ResponseEntity.noContent().build();
    }

}
