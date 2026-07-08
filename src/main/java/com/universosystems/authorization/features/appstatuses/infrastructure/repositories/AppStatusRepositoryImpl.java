package com.universosystems.authorization.features.appstatuses.infrastructure.repositories;

import com.universosystems.authorization.features.appstatuses.domain.models.AppStatus;
import com.universosystems.authorization.features.appstatuses.domain.repositories.AppStatusRepository;
import com.universosystems.authorization.features.appstatuses.infrastructure.entities.AppStatusEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppStatusRepositoryImpl implements AppStatusRepository {
    private final AppStatusJpaRepository repository;

    @Override
    public List<AppStatus> findAll() {
        return repository.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<AppStatus> findById(Integer id) {
        return repository.findById(id).map(this::toDomain);
    }

    @Override
    public Optional<AppStatus> findByName(String name) {
        return repository.findByNameIgnoreCase(name).map(this::toDomain);
    }

    @Override
    public AppStatus save(AppStatus appStatus) {
        return toDomain(repository.save(toEntity(appStatus)));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private AppStatus toDomain(AppStatusEntity entity) {
        return AppStatus.builder()
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

    private AppStatusEntity toEntity(AppStatus appStatus) {
        return AppStatusEntity.builder()
                .id(appStatus.getId())
                .name(appStatus.getName())
                .description(appStatus.getDescription())
                .isActive(Boolean.TRUE.equals(appStatus.getIsActive()))
                .createdBy(appStatus.getCreatedBy())
                .updatedBy(appStatus.getUpdatedBy())
                .build();
    }
}
