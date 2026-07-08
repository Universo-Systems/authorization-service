package com.universosystems.authorization.features.userroles.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRoleRequest {
    @NotNull
    private Integer idUser;

    @NotNull
    private Integer idRole;
}
