package com.universosystems.authorization.features.appstatuses.api.responses;

import java.time.OffsetDateTime;

public record AppStatusResponse(
        Integer id,
        String name,
        String description,
        Boolean isActive,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
