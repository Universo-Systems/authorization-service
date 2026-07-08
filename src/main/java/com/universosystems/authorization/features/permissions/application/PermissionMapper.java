package com.universosystems.authorization.features.permissions.application;

import com.universosystems.authorization.features.permissions.api.requests.PermissionRequest;
import com.universosystems.authorization.features.permissions.api.responses.PermissionResponse;
import com.universosystems.authorization.features.permissions.domain.models.Permission;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {
    public Permission toDomain(PermissionRequest request) {
        return Permission.builder()
                .idApp(request.getIdApp())
                .code(normalizeCode(request.getCode()))
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public PermissionResponse toResponse(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getIdApp(),
                permission.getAppCode(),
                permission.getCode(),
                permission.getName(),
                permission.getDescription(),
                permission.getCreatedAt(),
                permission.getUpdatedAt());
    }

    private String normalizeCode(String code) {
        return code == null ? null : code.trim().toLowerCase();
    }
}
