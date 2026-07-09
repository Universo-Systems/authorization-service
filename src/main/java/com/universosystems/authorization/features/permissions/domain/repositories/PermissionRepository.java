package com.universosystems.authorization.features.permissions.domain.repositories;

import com.universosystems.authorization.features.permissions.domain.models.Permission;

import java.util.List;
import java.util.Optional;

public interface PermissionRepository {
    List<Permission> findAll();

    List<Permission> findByAppId(Integer idApp);

    List<Permission> findByAppCode(String appCode);

    List<Permission> findEffectiveByUserIdAndAppCode(Integer idUser, String appCode);

    List<Permission> findEffectiveByRoleIdAndAppCode(Integer idRole, String appCode);

    Optional<Permission> findById(Integer id);

    Optional<Permission> findByAppIdAndCode(Integer idApp, String code);

    Permission save(Permission permission);

    void deleteById(Integer id);
}
