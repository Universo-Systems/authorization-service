package com.universosystems.authorization.features.superusers.api.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SuperUserRequest {
    @NotNull
    private Integer idUser;
}
