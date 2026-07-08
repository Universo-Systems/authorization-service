package com.universosystems.authorization.features.roles.api.responses;

import java.time.OffsetDateTime;

public record RoleResponse(
        Integer id,
        Integer idApp,
        String appCode,
        Integer idStatus,
        String statusName,
        String code,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
