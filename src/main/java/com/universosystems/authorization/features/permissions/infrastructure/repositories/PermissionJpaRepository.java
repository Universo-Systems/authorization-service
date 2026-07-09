package com.universosystems.authorization.features.permissions.infrastructure.repositories;

import com.universosystems.authorization.features.permissions.infrastructure.entities.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Integer> {
    List<PermissionEntity> findByDeletedAtIsNull();

    List<PermissionEntity> findByAppIdAndDeletedAtIsNull(Integer idApp);

    List<PermissionEntity> findByAppCodeIgnoreCaseAndDeletedAtIsNullAndAppDeletedAtIsNullOrderByCode(String appCode);

    Optional<PermissionEntity> findByIdAndDeletedAtIsNull(Integer id);

    Optional<PermissionEntity> findByAppIdAndCodeIgnoreCaseAndDeletedAtIsNull(Integer idApp, String code);

    @Query("""
            select distinct permission
            from PermissionEntity permission
            join RolePermissionEntity rolePermission on rolePermission.permission.id = permission.id
            join UserRoleEntity userRole on userRole.role.id = rolePermission.role.id
            join RoleEntity role on role.id = userRole.role.id
            join role.app app
            where userRole.idUser = :idUser
              and upper(app.code) = upper(:appCode)
              and userRole.deletedAt is null
              and role.deletedAt is null
              and upper(role.status.name) = 'ACTIVE'
              and permission.deletedAt is null
              and app.deletedAt is null
            order by permission.code
            """)
    List<PermissionEntity> findEffectiveByUserIdAndAppCode(@Param("idUser") Integer idUser,
            @Param("appCode") String appCode);

    @Query("""
            select distinct permission
            from PermissionEntity permission
            join RolePermissionEntity rolePermission on rolePermission.permission.id = permission.id
            join rolePermission.role role
            join role.app app
            where role.id = :idRole
              and upper(app.code) = upper(:appCode)
              and role.deletedAt is null
              and upper(role.status.name) = 'ACTIVE'
              and permission.deletedAt is null
              and app.deletedAt is null
            order by permission.code
            """)
    List<PermissionEntity> findEffectiveByRoleIdAndAppCode(@Param("idRole") Integer idRole,
            @Param("appCode") String appCode);
}
