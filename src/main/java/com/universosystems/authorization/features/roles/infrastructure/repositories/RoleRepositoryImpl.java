package com.universosystems.authorization.features.roles.infrastructure.repositories;

import com.universosystems.authorization.features.apps.infrastructure.entities.AppEntity;
import com.universosystems.authorization.features.rolestatuses.infrastructure.entities.RoleStatusEntity;
import com.universosystems.authorization.features.roles.domain.models.Role;
import com.universosystems.authorization.features.roles.domain.repositories.RoleRepository;
import com.universosystems.authorization.features.roles.infrastructure.entities.RoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleJpaRepository repository;

    @Override
    public List<Role> findAll() {
        return repository.findByDeletedAtIsNull().stream().map(this::toDomain).toList();
    }

    @Override
    public List<Role> findByAppId(Integer idApp) {
        return repository.findByAppIdAndDeletedAtIsNull(idApp).stream().map(this::toDomain).toList();
    }

    @Override
    public List<Role> findByUserIdAndAppCode(Integer idUser, String appCode) {
        return repository.findByUserIdAndAppCode(idUser, appCode).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<Role> findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(this::toDomain);
    }

    @Override
    public Optional<Role> findByAppIdAndCode(Integer idApp, String code) {
        return repository.findByAppIdAndCodeIgnoreCaseAndDeletedAtIsNull(idApp, code).map(this::toDomain);
    }

    @Override
    public Role save(Role role) {
        RoleEntity saved = repository.save(toEntity(role));
        return repository.findById(saved.getId()).map(this::toDomain).orElseGet(() -> toDomain(saved));
    }

    @Override
    public void deleteById(Integer id) {
        repository.findByIdAndDeletedAtIsNull(id).ifPresent(entity -> {
            entity.setDeletedAt(OffsetDateTime.now());
            repository.save(entity);
        });
    }

    private Role toDomain(RoleEntity entity) {
        return Role.builder()
                .id(entity.getId())
                .idApp(entity.getApp().getId())
                .appCode(entity.getApp().getCode())
                .idStatus(entity.getStatus().getId())
                .statusName(entity.getStatus().getName())
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

    private RoleEntity toEntity(Role role) {
        return RoleEntity.builder()
                .id(role.getId())
                .app(AppEntity.builder().id(role.getIdApp()).build())
                .status(RoleStatusEntity.builder().id(role.getIdStatus()).build())
                .code(role.getCode())
                .name(role.getName())
                .description(role.getDescription())
                .createdBy(role.getCreatedBy())
                .updatedBy(role.getUpdatedBy())
                .deletedAt(role.getDeletedAt())
                .deletedBy(role.getDeletedBy())
                .build();
    }
}
