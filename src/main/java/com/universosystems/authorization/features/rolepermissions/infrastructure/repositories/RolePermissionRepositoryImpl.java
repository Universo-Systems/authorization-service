package com.universosystems.authorization.features.rolepermissions.infrastructure.repositories;

import com.universosystems.authorization.features.permissions.infrastructure.entities.PermissionEntity;
import com.universosystems.authorization.features.rolepermissions.domain.models.RolePermission;
import com.universosystems.authorization.features.rolepermissions.domain.repositories.RolePermissionRepository;
import com.universosystems.authorization.features.rolepermissions.infrastructure.entities.RolePermissionEntity;
import com.universosystems.authorization.features.roles.infrastructure.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RolePermissionRepositoryImpl implements RolePermissionRepository {
    private final RolePermissionJpaRepository repository;

    @Override
    public List<RolePermission> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public List<RolePermission> findByRoleId(Integer idRole) {
        return repository.findByRoleId(idRole).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<RolePermission> findById(Integer id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<RolePermission> findByRoleIdAndPermissionId(Integer idRole, Integer idPermission) {
        return repository.findByRoleIdAndPermissionId(idRole, idPermission).map(this::toDomain);
    }

    @Override
    public RolePermission save(RolePermission rolePermission) {
        RolePermissionEntity saved = repository.save(toEntity(rolePermission));
        return repository.findById(saved.getId()).map(this::toDomain).orElseGet(() -> toDomain(saved));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private RolePermission toDomain(RolePermissionEntity entity) {
        return RolePermission.builder()
                .id(entity.getId())
                .idRole(entity.getRole().getId())
                .roleCode(entity.getRole().getCode())
                .idPermission(entity.getPermission().getId())
                .permissionCode(entity.getPermission().getCode())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .build();
    }

    private RolePermissionEntity toEntity(RolePermission rolePermission) {
        return RolePermissionEntity.builder()
                .id(rolePermission.getId())
                .role(RoleEntity.builder().id(rolePermission.getIdRole()).build())
                .permission(PermissionEntity.builder().id(rolePermission.getIdPermission()).build())
                .createdBy(rolePermission.getCreatedBy())
                .build();
    }
}
