package com.universosystems.authorization.features.userroles.infrastructure.repositories;

import com.universosystems.authorization.features.userroles.infrastructure.entities.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoleJpaRepository extends JpaRepository<UserRoleEntity, Integer> {
    List<UserRoleEntity> findByDeletedAtIsNull();

    List<UserRoleEntity> findByIdUserAndDeletedAtIsNull(Integer idUser);

    List<UserRoleEntity> findByRoleIdAndDeletedAtIsNull(Integer idRole);

    Optional<UserRoleEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<UserRoleEntity> findByIdUserAndRoleIdAndDeletedAtIsNull(Integer idUser, Integer idRole);

    Optional<UserRoleEntity> findByIdUserAndRoleId(Integer idUser, Integer idRole);
}
