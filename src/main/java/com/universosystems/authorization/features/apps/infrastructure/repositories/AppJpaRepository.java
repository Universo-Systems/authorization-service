package com.universosystems.authorization.features.apps.infrastructure.repositories;

import com.universosystems.authorization.features.apps.infrastructure.entities.AppEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AppJpaRepository extends JpaRepository<AppEntity, Integer> {
    List<AppEntity> findByDeletedAtIsNull();

    @Query("""
            select distinct app
            from UserRoleEntity userRole
            join userRole.role role
            join role.app app
            join role.status roleStatus
            join app.status appStatus
            where userRole.idUser = :idUser
              and userRole.deletedAt is null
              and role.deletedAt is null
              and app.deletedAt is null
              and upper(roleStatus.name) = 'ACTIVE'
              and upper(appStatus.name) = 'ACTIVE'
            order by app.name
            """)
    List<AppEntity> findByUserId(@Param("idUser") Integer idUser);

    Optional<AppEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<AppEntity> findByCodeIgnoreCase(String code);
}
