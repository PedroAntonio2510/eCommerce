package io.github.api.controller;

import io.github.api.domain.User;
import io.github.api.domain.dto.UserRequestDTO;
import io.github.api.domain.mapper.UserMapper;
import io.github.api.security.AuthenticationService;
import io.github.api.service.UserService;
import io.github.api.validator.UserValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "User Controller")
public class UserController implements GenericController {

    private final UserService service;
    private final UserValidator validator;
    private final UserMapper userMapper;
    private final AuthenticationService authenticationService;

    @GetMapping("/verify")
    public String verifyUser(@Param("code") String code) {
        if (validator.validateVerificationCode(code)) {
            return "verify_sucess";
        } else {
            return "verify_fail";
        }
    }

    @PostMapping("/authenticate")
    public String authenticate(Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody @Valid UserRequestDTO request) {
        User user = userMapper.toEntity(request);
        URI uri = headerLocation(user.getId());
        service.registerUser(user);
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/manager")
    public ResponseEntity<?> saveUserManager(@RequestBody @Valid UserRequestDTO request) {
        User user = userMapper.toEntity(request);
        URI uri = headerLocation(user.getId());
        service.createAdminUser(user);
        return ResponseEntity.created(uri).build();
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        @RequestBody @Valid UserRequestDTO request) {
        return service.getById(id)
                .map(user -> {
                    User newUser = userMapper.toEntity(request);
                    user.setName(newUser.getName());
                    user.setLastName(newUser.getLastName());
                    user.setCpf(newUser.getCpf());
                    user.setEmail(newUser.getEmail());
                    user.setPhoneNumber(newUser.getPhoneNumber());
                    user.setPassword(newUser.getPassword());
                    user.setLogin(newUser.getLogin());

                    service.registerUser(user);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        Optional<User> userFound = service.getById(id);
        service.deleteUser(userFound.get().getId());
        return ResponseEntity.noContent().build();
    }

}
