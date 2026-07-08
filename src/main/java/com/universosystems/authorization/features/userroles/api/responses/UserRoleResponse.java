package com.universosystems.authorization.features.userroles.api.responses;

import java.time.OffsetDateTime;

public record UserRoleResponse(
        Integer id,
        Integer idUser,
        Integer idRole,
        String roleCode,
        String appCode,
        OffsetDateTime createdAt) {
}
