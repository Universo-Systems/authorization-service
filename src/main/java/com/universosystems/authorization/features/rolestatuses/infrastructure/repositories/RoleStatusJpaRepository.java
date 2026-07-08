package com.universosystems.authorization.features.rolestatuses.infrastructure.repositories;

import com.universosystems.authorization.features.rolestatuses.infrastructure.entities.RoleStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleStatusJpaRepository extends JpaRepository<RoleStatusEntity, Integer> {
    Optional<RoleStatusEntity> findByNameIgnoreCase(String name);
}
