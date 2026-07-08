package com.universosystems.authorization.features.rolepermissions.api.controllers;

import com.universosystems.authorization.features.rolepermissions.api.requests.RolePermissionRequest;
import com.universosystems.authorization.features.rolepermissions.api.responses.RolePermissionResponse;
import com.universosystems.authorization.features.rolepermissions.application.RolePermissionMapper;
import com.universosystems.authorization.features.rolepermissions.application.RolePermissionService;
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
@RequestMapping("/api/role-permissions")
@RequiredArgsConstructor
public class RolePermissionController {
    private final RolePermissionService service;
    private final RolePermissionMapper mapper;

    @GetMapping
    public List<RolePermissionResponse> obtenerTodos(@RequestParam(required = false) Integer roleId) {
        return service.findAll(roleId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RolePermissionResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RolePermissionResponse> crear(@Valid @RequestBody RolePermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
