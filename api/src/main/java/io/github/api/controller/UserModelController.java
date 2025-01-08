package io.github.api.controller;

import io.github.api.domain.UserModel;
import io.github.api.domain.dto.UserModelRequestDTO;
import io.github.api.domain.mapper.UserModelMapper;
import io.github.api.service.UserModelService;
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
public class UserModelController implements GenericController {

    private final UserModelService service;
    private final UserModelMapper userModelMapper;

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserModelRequestDTO request) {
        UserModel userModel = userModelMapper.toEntity(request);
        URI uri = headerLocation(userModel.getId());
        service.createUser(userModel);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/manager")
    public ResponseEntity<?> saveUserManager(@RequestBody @Valid UserModelRequestDTO request) {
        UserModel userModel = userModelMapper.toEntity(request);
        URI uri = headerLocation(userModel.getId());
        service.createAdminUser(userModel);
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<UserModel> userFound = service.getById(id);
        service.deleteUser(userFound.get().getId());
        return ResponseEntity.noContent().build();
    }

}
