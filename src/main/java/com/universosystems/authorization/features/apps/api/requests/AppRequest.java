package com.universosystems.authorization.features.apps.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppRequest {
    @NotNull
    private Integer idStatus;

    @NotBlank
    @Size(max = 80)
    private String code;

    @NotBlank
    @Size(max = 120)
    private String name;

    @Size(max = 250)
    private String description;
}
