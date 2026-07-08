package com.universosystems.authorization.features.roles.domain.repositories;

import com.universosystems.authorization.features.roles.domain.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    List<Role> findAll();

    List<Role> findByAppId(Integer idApp);

    Optional<Role> findById(Integer id);

    Optional<Role> findByAppIdAndCode(Integer idApp, String code);

    Role save(Role role);

    void deleteById(Integer id);
}
