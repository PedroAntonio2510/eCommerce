package io.github.api.controller;

import io.github.api.domain.UserModel;
import io.github.api.domain.dto.UserModelRequestDTO;
import io.github.api.domain.mapper.UserModelMapper;
import io.github.api.service.UserModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
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


}
