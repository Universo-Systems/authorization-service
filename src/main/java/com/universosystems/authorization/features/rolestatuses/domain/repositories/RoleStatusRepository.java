package com.universosystems.authorization.features.rolestatuses.domain.repositories;

import com.universosystems.authorization.features.rolestatuses.domain.models.RoleStatus;

import java.util.List;
import java.util.Optional;

public interface RoleStatusRepository {
    List<RoleStatus> findAll();

    Optional<RoleStatus> findById(Integer id);

    Optional<RoleStatus> findByName(String name);

    RoleStatus save(RoleStatus roleStatus);

    void deleteById(Integer id);
}
