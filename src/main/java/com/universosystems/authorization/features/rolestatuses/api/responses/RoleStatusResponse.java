package com.universosystems.authorization.features.rolestatuses.api.responses;

import java.time.OffsetDateTime;

public record RoleStatusResponse(
        Integer id,
        String name,
        String description,
        Boolean isActive,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
