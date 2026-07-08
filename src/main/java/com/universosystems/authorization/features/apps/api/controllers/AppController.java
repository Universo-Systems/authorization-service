package com.universosystems.authorization.features.apps.api.controllers;

import com.universosystems.authorization.features.apps.api.requests.AppRequest;
import com.universosystems.authorization.features.apps.api.responses.AppResponse;
import com.universosystems.authorization.features.apps.application.AppMapper;
import com.universosystems.authorization.features.apps.application.AppService;
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
@RequestMapping("/api/apps")
@RequiredArgsConstructor
public class AppController {
    private final AppService service;
    private final AppMapper mapper;

    @GetMapping
    public List<AppResponse> obtenerTodos() {
        return service.findAll().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public AppResponse obtenerPorId(@PathVariable Integer id) {
        return mapper.toResponse(service.findById(id));
    }

    @GetMapping("/by-code")
    public AppResponse obtenerPorCodigo(@RequestParam String code) {
        return mapper.toResponse(service.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<AppResponse> crear(@Valid @RequestBody AppRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(service.create(mapper.toDomain(request))));
    }

    @PutMapping("/{id}")
    public AppResponse actualizar(@PathVariable Integer id, @Valid @RequestBody AppRequest request) {
        return mapper.toResponse(service.update(id, mapper.toDomain(request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
