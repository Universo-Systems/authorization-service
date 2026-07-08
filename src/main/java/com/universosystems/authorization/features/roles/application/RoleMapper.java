package com.universosystems.authorization.features.roles.application;

import com.universosystems.authorization.features.roles.api.requests.RoleRequest;
import com.universosystems.authorization.features.roles.api.responses.RoleResponse;
import com.universosystems.authorization.features.roles.domain.models.Role;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {
    public Role toDomain(RoleRequest request) {
        return Role.builder()
                .idApp(request.getIdApp())
                .idStatus(request.getIdStatus())
                .code(normalizeCode(request.getCode()))
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getIdApp(),
                role.getAppCode(),
                role.getIdStatus(),
                role.getStatusName(),
                role.getCode(),
                role.getName(),
                role.getDescription(),
                role.getCreatedAt(),
                role.getUpdatedAt());
    }

    private String normalizeCode(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }
}
