package com.universosystems.authorization.features.permissions.api.controllers;

import com.universosystems.authorization.features.permissions.api.requests.PermissionRequest;
import com.universosystems.authorization.features.permissions.api.responses.PermissionResponse;
import com.universosystems.authorization.features.permissions.application.PermissionMapper;
import com.universosystems.authorization.features.permissions.application.PermissionService;
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
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService service;
    private final PermissionMapper mapper;

    @GetMapping
    public List<PermissionResponse> obtenerTodos(@RequestParam(required = false) Integer appId) {
        return service.findAll(appId).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/effective")
    public List<PermissionResponse> obtenerEfectivos(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer roleId,
            @RequestParam String appCode) {
        if (roleId != null) {
            return service.findEffectiveByRoleIdAndAppCode(roleId, appCode).stream().map(mapper::toResponse).toList();
        }
        return service.findEffectiveByUserIdAndAppCode(userId, appCode).stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PermissionResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> crear(@Valid @RequestBody PermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @PutMapping("/{id}")
    public PermissionResponse actualizar(@PathVariable Integer id, @Valid @RequestBody PermissionRequest request) {
        return mapper.toResponse(service.update(id, mapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
