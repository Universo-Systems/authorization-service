package com.universosystems.authorization.features.rolepermissions.infrastructure.entities;

import com.universosystems.authorization.features.permissions.infrastructure.entities.PermissionEntity;
import com.universosystems.authorization.features.roles.infrastructure.entities.RoleEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Entity
@Table(name = "role_permissions",
        uniqueConstraints = @UniqueConstraint(name = "uk_role_permissions_role_permission",
                columnNames = { "id_role", "id_permission" }))
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_role", nullable = false)
    private RoleEntity role;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_permission", nullable = false)
    private PermissionEntity permission;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private OffsetDateTime createdAt;

    @Column(name = "created_by")
    private Integer createdBy;
}
