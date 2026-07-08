package com.universosystems.authorization.features.superusers.infrastructure.repositories;

import com.universosystems.authorization.features.superusers.infrastructure.entities.SuperUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SuperUserJpaRepository extends JpaRepository<SuperUserEntity, Integer> {
    List<SuperUserEntity> findByDeletedAtIsNull();

    List<SuperUserEntity> findAllByIdUserAndDeletedAtIsNull(Integer idUser);

    Optional<SuperUserEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<SuperUserEntity> findByIdUserAndDeletedAtIsNull(Integer idUser);

    Optional<SuperUserEntity> findByIdUser(Integer idUser);
}
