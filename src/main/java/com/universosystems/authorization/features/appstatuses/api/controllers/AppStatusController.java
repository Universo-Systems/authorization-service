package com.universosystems.authorization.features.appstatuses.api.controllers;

import com.universosystems.authorization.features.appstatuses.api.requests.AppStatusRequest;
import com.universosystems.authorization.features.appstatuses.api.responses.AppStatusResponse;
import com.universosystems.authorization.features.appstatuses.application.AppStatusMapper;
import com.universosystems.authorization.features.appstatuses.application.AppStatusService;
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
@RequestMapping("/api/app-statuses")
@RequiredArgsConstructor
public class AppStatusController {
    private final AppStatusService service;
    private final AppStatusMapper mapper;

    @GetMapping
    public List<AppStatusResponse> obtenerTodos() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AppStatusResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<AppStatusResponse> crear(@Valid @RequestBody AppStatusRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @PutMapping("/{id}")
    public AppStatusResponse actualizar(@PathVariable Integer id, @Valid @RequestBody AppStatusRequest request) {
        return mapper.toResponse(service.update(id, mapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
