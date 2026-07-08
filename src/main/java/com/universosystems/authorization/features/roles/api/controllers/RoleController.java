package com.universosystems.authorization.features.roles.api.controllers;

import com.universosystems.authorization.features.roles.api.requests.RoleRequest;
import com.universosystems.authorization.features.roles.api.responses.RoleResponse;
import com.universosystems.authorization.features.roles.application.RoleMapper;
import com.universosystems.authorization.features.roles.application.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService service;
    private final RoleMapper mapper;

    @GetMapping
    public List<RoleResponse> obtenerTodos(@RequestParam(required = false) Integer appId) {
        return service.findAll(appId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RoleResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleResponse> crear(@Valid @RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @PutMapping("/{id}")
    public RoleResponse actualizar(@PathVariable Integer id, @Valid @RequestBody RoleRequest request) {
        return mapper.toResponse(service.update(id, mapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
