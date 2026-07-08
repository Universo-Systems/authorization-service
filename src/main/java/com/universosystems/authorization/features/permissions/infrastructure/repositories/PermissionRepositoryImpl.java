package com.universosystems.authorization.features.permissions.infrastructure.repositories;

import com.universosystems.authorization.features.apps.infrastructure.entities.AppEntity;
import com.universosystems.authorization.features.permissions.domain.models.Permission;
import com.universosystems.authorization.features.permissions.domain.repositories.PermissionRepository;
import com.universosystems.authorization.features.permissions.infrastructure.entities.PermissionEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PermissionRepositoryImpl implements PermissionRepository {
    private final PermissionJpaRepository repository;

    @Override
    public List<Permission> findAll() {
        return repository.findByDeletedAtIsNull().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Permission> findByAppId(Integer idApp) {
        return repository.findByAppIdAndDeletedAtIsNull(idApp).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Permission> findByAppCode(String appCode) {
        return repository.findByAppCodeIgnoreCaseAndDeletedAtIsNullAndAppDeletedAtIsNullOrderByCode(appCode).stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Permission> findEffectiveByUserIdAndAppCode(Integer idUser, String appCode) {
        return repository.findEffectiveByUserIdAndAppCode(idUser, appCode).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Permission> findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(this::toDomain);
    }

    @Override
    public Optional<Permission> findByAppIdAndCode(Integer idApp, String code) {
        return repository.findByAppIdAndCodeIgnoreCaseAndDeletedAtIsNull(idApp, code).map(this::toDomain);
    }

    @Override
    public Permission save(Permission permission) {
        PermissionEntity saved = repository.save(toEntity(permission));
        return repository.findById(saved.getId()).map(this::toDomain).orElseGet(() -> toDomain(saved));
    }

    @Override
    public void deleteById(Integer id) {
        repository.findByIdAndDeletedAtIsNull(id).ifPresent(entity -> {
            entity.setDeletedAt(OffsetDateTime.now());
            repository.save(entity);
        });
    }

    private Permission toDomain(PermissionEntity entity) {
        return Permission.builder()
                .id(entity.getId())
                .idApp(entity.getApp().getId())
                .appCode(entity.getApp().getCode())
                .code(entity.getCode())
                .name(entity.getName())
                .description(entity.getDescription())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .build();
    }

    private PermissionEntity toEntity(Permission permission) {
        return PermissionEntity.builder()
                .id(permission.getId())
                .app(AppEntity.builder().id(permission.getIdApp()).build())
                .code(permission.getCode())
                .name(permission.getName())
                .description(permission.getDescription())
                .createdBy(permission.getCreatedBy())
                .updatedBy(permission.getUpdatedBy())
                .deletedAt(permission.getDeletedAt())
                .deletedBy(permission.getDeletedBy())
                .build();
    }
}
