package com.universosystems.authorization.features.permissions.api.responses;

import java.time.OffsetDateTime;

public record PermissionResponse(
        Integer id,
        Integer idApp,
        String appCode,
        String code,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
