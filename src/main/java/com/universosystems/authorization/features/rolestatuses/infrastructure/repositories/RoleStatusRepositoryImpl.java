package com.universosystems.authorization.features.rolestatuses.infrastructure.repositories;

import com.universosystems.authorization.features.rolestatuses.domain.models.RoleStatus;
import com.universosystems.authorization.features.rolestatuses.domain.repositories.RoleStatusRepository;
import com.universosystems.authorization.features.rolestatuses.infrastructure.entities.RoleStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoleStatusRepositoryImpl implements RoleStatusRepository {
    private final RoleStatusJpaRepository repository;

    @Override
    public List<RoleStatus> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<RoleStatus> findById(Integer id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<RoleStatus> findByName(String name) {
        return repository.findByNameIgnoreCase(name).map(this::toDomain);
    }

    @Override
    public RoleStatus save(RoleStatus roleStatus) {
        return toDomain(repository.save(toEntity(roleStatus)));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private RoleStatus toDomain(RoleStatusEntity entity) {
        return RoleStatus.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .isActive(entity.getIsActive())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .updatedAt(entity.getUpdatedAt())
                .updatedBy(entity.getUpdatedBy())
                .build();
    }

    private RoleStatusEntity toEntity(RoleStatus roleStatus) {
        return RoleStatusEntity.builder()
                .id(roleStatus.getId())
                .name(roleStatus.getName())
                .description(roleStatus.getDescription())
                .isActive(Boolean.TRUE.equals(roleStatus.getIsActive()))
                .createdBy(roleStatus.getCreatedBy())
                .updatedBy(roleStatus.getUpdatedBy())
                .build();
    }
}
