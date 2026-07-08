package com.universosystems.authorization.features.apps.api.responses;

import java.time.OffsetDateTime;

public record AppResponse(
        Integer id,
        Integer idStatus,
        String statusName,
        String code,
        String name,
        String description,
        OffsetDateTime createdAt,
        OffsetDateTime updatedAt) {
}
