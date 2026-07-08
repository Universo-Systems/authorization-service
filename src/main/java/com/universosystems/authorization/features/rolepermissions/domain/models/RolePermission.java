package com.universosystems.authorization.features.rolepermissions.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {
    private Integer id;
    private Integer idRole;
    private String roleCode;
    private Integer idPermission;
    private String permissionCode;
    private OffsetDateTime createdAt;
    private Integer createdBy;
}
