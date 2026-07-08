package com.universosystems.authorization.features.rolepermissions.api.responses;

import java.time.OffsetDateTime;

public record RolePermissionResponse(
        Integer id,
        Integer idRole,
        String roleCode,
        Integer idPermission,
        String permissionCode,
        OffsetDateTime createdAt) {
}
