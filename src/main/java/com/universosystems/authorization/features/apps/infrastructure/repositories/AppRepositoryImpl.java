package com.universosystems.authorization.features.apps.infrastructure.repositories;

import com.universosystems.authorization.features.appstatuses.infrastructure.entities.AppStatusEntity;
import com.universosystems.authorization.features.apps.domain.models.App;
import com.universosystems.authorization.features.apps.domain.repositories.AppRepository;
import com.universosystems.authorization.features.apps.infrastructure.entities.AppEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AppRepositoryImpl implements AppRepository {
    private final AppJpaRepository repository;

    @Override
    public List<App> findAll() {
        return repository.findByDeletedAtIsNull().stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<App> findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(this::toDomain);
    }

    @Override
    public Optional<App> findByCode(String code) {
        return repository.findByCodeIgnoreCase(code).filter(entity -> entity.getDeletedAt() == null).map(this::toDomain);
    }

    @Override
    public App save(App app) {
        AppEntity saved = repository.save(toEntity(app));
        return repository.findById(saved.getId()).map(this::toDomain).orElseGet(() -> toDomain(saved));
    }

    @Override
    public void deleteById(Integer id) {
        repository.findByIdAndDeletedAtIsNull(id).ifPresent(entity -> {
            entity.setDeletedAt(OffsetDateTime.now());
            repository.save(entity);
        });
    }

    private App toDomain(AppEntity entity) {
        return App.builder()
                .id(entity.getId())
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

    private AppEntity toEntity(App app) {
        return AppEntity.builder()
                .id(app.getId())
                .status(AppStatusEntity.builder().id(app.getIdStatus()).build())
                .code(app.getCode())
                .name(app.getName())
                .description(app.getDescription())
                .createdBy(app.getCreatedBy())
                .updatedBy(app.getUpdatedBy())
                .deletedAt(app.getDeletedAt())
                .deletedBy(app.getDeletedBy())
                .build();
    }
}
