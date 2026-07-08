package com.universosystems.authorization.features.superusers.api.responses;

import java.time.OffsetDateTime;

public record SuperUserResponse(
        Integer id,
        Integer idUser,
        OffsetDateTime createdAt) {
}
