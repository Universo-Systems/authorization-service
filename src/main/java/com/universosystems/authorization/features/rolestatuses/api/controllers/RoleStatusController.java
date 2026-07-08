package com.universosystems.authorization.features.rolestatuses.api.controllers;

import com.universosystems.authorization.features.rolestatuses.api.requests.RoleStatusRequest;
import com.universosystems.authorization.features.rolestatuses.api.responses.RoleStatusResponse;
import com.universosystems.authorization.features.rolestatuses.application.RoleStatusMapper;
import com.universosystems.authorization.features.rolestatuses.application.RoleStatusService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/role-statuses")
@RequiredArgsConstructor
public class RoleStatusController {
    private final RoleStatusService service;
    private final RoleStatusMapper mapper;

    @GetMapping
    public List<RoleStatusResponse> obtenerTodos() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RoleStatusResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleStatusResponse> crear(@Valid @RequestBody RoleStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @PutMapping("/{id}")
    public RoleStatusResponse actualizar(@PathVariable Integer id, @Valid @RequestBody RoleStatusRequest request) {
        return mapper.toResponse(service.update(id, mapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
