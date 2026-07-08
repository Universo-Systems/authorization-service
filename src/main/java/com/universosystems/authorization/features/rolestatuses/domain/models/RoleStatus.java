package com.universosystems.authorization.features.rolestatuses.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleStatus {
    private Integer id;
    private String name;
    private String description;
    private Boolean isActive;
    private OffsetDateTime createdAt;
    private Integer createdBy;
    private OffsetDateTime updatedAt;
    private Integer updatedBy;
}
