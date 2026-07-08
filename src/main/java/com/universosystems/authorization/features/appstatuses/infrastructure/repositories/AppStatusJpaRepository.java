package com.universosystems.authorization.features.appstatuses.infrastructure.repositories;

import com.universosystems.authorization.features.appstatuses.infrastructure.entities.AppStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppStatusJpaRepository extends JpaRepository<AppStatusEntity, Integer> {
    Optional<AppStatusEntity> findByNameIgnoreCase(String name);
}
