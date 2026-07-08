package com.universosystems.authorization.features.permissions.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PermissionRequest {
    @NotNull
    private Integer idApp;

    @NotBlank
    @Size(max = 120)
    private String code;

    @NotBlank
    @Size(max = 120)
    private String name;

    @Size(max = 250)
    private String description;
}
