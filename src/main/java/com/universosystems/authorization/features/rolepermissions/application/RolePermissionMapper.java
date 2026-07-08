package com.universosystems.authorization.features.rolepermissions.application;

import com.universosystems.authorization.features.rolepermissions.api.requests.RolePermissionRequest;
import com.universosystems.authorization.features.rolepermissions.api.responses.RolePermissionResponse;
import com.universosystems.authorization.features.rolepermissions.domain.models.RolePermission;
import org.springframework.stereotype.Component;

@Component
public class RolePermissionMapper {
    public RolePermission toDomain(RolePermissionRequest request) {
        return RolePermission.builder()
                .idRole(request.getIdRole())
                .idPermission(request.getIdPermission())
                .build();
    }

    public RolePermissionResponse toResponse(RolePermission rolePermission) {
        return new RolePermissionResponse(
                rolePermission.getId(),
                rolePermission.getIdRole(),
                rolePermission.getRoleCode(),
                rolePermission.getIdPermission(),
                rolePermission.getPermissionCode(),
                rolePermission.getCreatedAt());
    }
}
