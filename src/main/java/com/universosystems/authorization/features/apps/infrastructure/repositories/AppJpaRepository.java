package com.universosystems.authorization.features.apps.infrastructure.repositories;

import com.universosystems.authorization.features.apps.infrastructure.entities.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppJpaRepository extends JpaRepository<AppEntity, Integer> {
    List<AppEntity> findByDeletedAtIsNull();

    Optional<AppEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<AppEntity> findByCodeIgnoreCase(String code);
}
