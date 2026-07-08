package com.universosystems.authorization.features.rolepermissions.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RolePermissionRequest {
    @NotNull
    private Integer idRole;

    @NotNull
    private Integer idPermission;
}
