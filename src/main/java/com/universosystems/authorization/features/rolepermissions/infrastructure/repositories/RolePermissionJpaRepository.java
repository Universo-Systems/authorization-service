package com.universosystems.authorization.features.rolepermissions.infrastructure.repositories;

import com.universosystems.authorization.features.rolepermissions.infrastructure.entities.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolePermissionJpaRepository extends JpaRepository<RolePermissionEntity, Integer> {
    List<RolePermissionEntity> findByRoleId(Integer idRole);

    Optional<RolePermissionEntity> findByRoleIdAndPermissionId(Integer idRole, Integer idPermission);
}
