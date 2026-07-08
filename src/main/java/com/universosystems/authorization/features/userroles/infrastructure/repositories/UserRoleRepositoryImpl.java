package com.universosystems.authorization.features.userroles.infrastructure.repositories;

import com.universosystems.authorization.features.roles.infrastructure.entities.RoleEntity;
import com.universosystems.authorization.features.userroles.domain.models.UserRole;
import com.universosystems.authorization.features.userroles.domain.repositories.UserRoleRepository;
import com.universosystems.authorization.features.userroles.infrastructure.entities.UserRoleEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRoleRepositoryImpl implements UserRoleRepository {
    private final UserRoleJpaRepository repository;

    @Override
    public List<UserRole> findAll() {
        return repository.findByDeletedAtIsNull().stream().map(this::toDomain).toList();
    }

    @Override
    public List<UserRole> findByUserId(Integer idUser) {
        return repository.findByIdUserAndDeletedAtIsNull(idUser).stream().map(this::toDomain).toList();
    }

    @Override
    public List<UserRole> findByRoleId(Integer idRole) {
        return repository.findByRoleIdAndDeletedAtIsNull(idRole).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<UserRole> findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(this::toDomain);
    }

    @Override
    public Optional<UserRole> findActiveByUserIdAndRoleId(Integer idUser, Integer idRole) {
        return repository.findByIdUserAndRoleIdAndDeletedAtIsNull(idUser, idRole).map(this::toDomain);
    }

    @Override
    public Optional<UserRole> findAnyByUserIdAndRoleId(Integer idUser, Integer idRole) {
        return repository.findByIdUserAndRoleId(idUser, idRole).map(this::toDomain);
    }

    @Override
    public UserRole save(UserRole userRole) {
        UserRoleEntity saved = repository.save(toEntity(userRole));
        return repository.findById(saved.getId()).map(this::toDomain).orElseGet(() -> toDomain(saved));
    }

    @Override
    public void deleteById(Integer id) {
        repository.findByIdAndDeletedAtIsNull(id).ifPresent(entity -> {
            entity.setDeletedAt(OffsetDateTime.now());
            repository.save(entity);
        });
    }

    private UserRole toDomain(UserRoleEntity entity) {
        return UserRole.builder()
                .id(entity.getId())
                .idUser(entity.getIdUser())
                .idRole(entity.getRole().getId())
                .roleCode(entity.getRole().getCode())
                .appCode(entity.getRole().getApp().getCode())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .build();
    }

    private UserRoleEntity toEntity(UserRole userRole) {
        return UserRoleEntity.builder()
                .id(userRole.getId())
                .idUser(userRole.getIdUser())
                .role(RoleEntity.builder().id(userRole.getIdRole()).build())
                .createdBy(userRole.getCreatedBy())
                .deletedAt(userRole.getDeletedAt())
                .deletedBy(userRole.getDeletedBy())
                .build();
    }
}
