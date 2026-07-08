package com.universosystems.authorization.features.rolepermissions.domain.repositories;

import com.universosystems.authorization.features.rolepermissions.domain.models.RolePermission;

import java.util.List;
import java.util.Optional;

public interface RolePermissionRepository {
    List<RolePermission> findAll();

    List<RolePermission> findByRoleId(Integer idRole);

    Optional<RolePermission> findById(Integer id);

    Optional<RolePermission> findByRoleIdAndPermissionId(Integer idRole, Integer idPermission);

    RolePermission save(RolePermission rolePermission);

    void deleteById(Integer id);
}
