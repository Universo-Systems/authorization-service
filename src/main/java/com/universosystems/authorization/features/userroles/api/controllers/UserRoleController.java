package com.universosystems.authorization.features.userroles.api.controllers;

import com.universosystems.authorization.features.userroles.api.requests.UserRoleRequest;
import com.universosystems.authorization.features.userroles.api.responses.UserRoleResponse;
import com.universosystems.authorization.features.userroles.application.UserRoleMapper;
import com.universosystems.authorization.features.userroles.application.UserRoleService;
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
@RequestMapping("/api/user-roles")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService service;
    private final UserRoleMapper mapper;

    @GetMapping
    public List<UserRoleResponse> obtenerTodos(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer roleId) {
        return service.findAll(userId, roleId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public UserRoleResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<UserRoleResponse> crear(@Valid @RequestBody UserRoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
