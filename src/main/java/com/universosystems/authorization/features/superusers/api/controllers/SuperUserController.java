package com.universosystems.authorization.features.superusers.api.controllers;

import com.universosystems.authorization.features.superusers.api.requests.SuperUserRequest;
import com.universosystems.authorization.features.superusers.api.responses.SuperUserResponse;
import com.universosystems.authorization.features.superusers.application.SuperUserMapper;
import com.universosystems.authorization.features.superusers.application.SuperUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/super-users")
@RequiredArgsConstructor
public class SuperUserController {
    private final SuperUserService service;
    private final SuperUserMapper mapper;

    @GetMapping
    public List<SuperUserResponse> obtenerTodos(@RequestParam(required = false) Integer userId) {
        return service.findAll(userId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public SuperUserResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<SuperUserResponse> crear(@Valid @RequestBody SuperUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
