package com.universosystems.authorization.features.roles.infrastructure.repositories;

import com.universosystems.authorization.features.roles.infrastructure.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findByDeletedAtIsNull();

    List<RoleEntity> findByAppIdAndDeletedAtIsNull(Integer idApp);

    Optional<RoleEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<RoleEntity> findByAppIdAndCodeIgnoreCaseAndDeletedAtIsNull(Integer idApp, String code);
}
