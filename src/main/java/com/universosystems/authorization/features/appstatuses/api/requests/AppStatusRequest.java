package com.universosystems.authorization.features.appstatuses.api.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AppStatusRequest {
    @NotBlank
    @Size(max = 60)
    private String name;

    @Size(max = 250)
    private String description;

    @NotNull
    private Boolean isActive = true;
}
