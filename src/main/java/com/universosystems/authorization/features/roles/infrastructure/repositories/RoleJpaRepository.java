package com.universosystems.authorization.features.roles.infrastructure.repositories;

import com.universosystems.authorization.features.roles.infrastructure.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, Integer> {
    List<RoleEntity> findByDeletedAtIsNull();

    List<RoleEntity> findByAppIdAndDeletedAtIsNull(Integer idApp);

    @Query("""
            select role
            from RoleEntity role
            join role.status roleStatus
            where role.app.id = :idApp
              and role.deletedAt is null
              and upper(roleStatus.name) = 'ACTIVE'
            order by role.name
            """)
    List<RoleEntity> findActiveByAppId(@Param("idApp") Integer idApp);

    @Query("""
            select distinct role
            from UserRoleEntity userRole
            join userRole.role role
            join role.app app
            join role.status roleStatus
            join app.status appStatus
            where userRole.idUser = :idUser
              and upper(app.code) = upper(:appCode)
              and userRole.deletedAt is null
              and role.deletedAt is null
              and app.deletedAt is null
              and upper(roleStatus.name) = 'ACTIVE'
              and upper(appStatus.name) = 'ACTIVE'
            order by role.name
            """)
    List<RoleEntity> findByUserIdAndAppCode(@Param("idUser") Integer idUser, @Param("appCode") String appCode);

    Optional<RoleEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<RoleEntity> findByAppIdAndCodeIgnoreCaseAndDeletedAtIsNull(Integer idApp, String code);
}
