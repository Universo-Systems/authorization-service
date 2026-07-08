package com.universosystems.authorization.features.userroles.domain.repositories;

import com.universosystems.authorization.features.userroles.domain.models.UserRole;

import java.util.List;
import java.util.Optional;

public interface UserRoleRepository {
    List<UserRole> findAll();

    List<UserRole> findByUserId(Integer idUser);

    List<UserRole> findByRoleId(Integer idRole);

    Optional<UserRole> findById(Integer id);

    Optional<UserRole> findActiveByUserIdAndRoleId(Integer idUser, Integer idRole);

    Optional<UserRole> findAnyByUserIdAndRoleId(Integer idUser, Integer idRole);

    UserRole save(UserRole userRole);

    void deleteById(Integer id);
}
