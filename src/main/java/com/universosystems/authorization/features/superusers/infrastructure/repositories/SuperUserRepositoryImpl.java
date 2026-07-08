package com.universosystems.authorization.features.superusers.infrastructure.repositories;

import com.universosystems.authorization.features.superusers.domain.models.SuperUser;
import com.universosystems.authorization.features.superusers.domain.repositories.SuperUserRepository;
import com.universosystems.authorization.features.superusers.infrastructure.entities.SuperUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SuperUserRepositoryImpl implements SuperUserRepository {
    private final SuperUserJpaRepository repository;

    @Override
    public List<SuperUser> findAll() {
        return repository.findByDeletedAtIsNull().stream().map(this::toDomain).toList();
    }

    @Override
    public List<SuperUser> findByUserId(Integer idUser) {
        return repository.findAllByIdUserAndDeletedAtIsNull(idUser).stream().map(this::toDomain).toList();
    }

    @Override
    public Optional<SuperUser> findById(Integer id) {
        return repository.findByIdAndDeletedAtIsNull(id).map(this::toDomain);
    }

    @Override
    public Optional<SuperUser> findActiveByUserId(Integer idUser) {
        return repository.findByIdUserAndDeletedAtIsNull(idUser).map(this::toDomain);
    }

    @Override
    public Optional<SuperUser> findAnyByUserId(Integer idUser) {
        return repository.findByIdUser(idUser).map(this::toDomain);
    }

    @Override
    public SuperUser save(SuperUser superUser) {
        SuperUserEntity saved = repository.save(toEntity(superUser));
        return repository.findById(saved.getId()).map(this::toDomain).orElseGet(() -> toDomain(saved));
    }

    @Override
    public void deleteById(Integer id) {
        repository.findByIdAndDeletedAtIsNull(id).ifPresent(entity -> {
            entity.setDeletedAt(OffsetDateTime.now());
            repository.save(entity);
        });
    }

    private SuperUser toDomain(SuperUserEntity entity) {
        return SuperUser.builder()
                .id(entity.getId())
                .idUser(entity.getIdUser())
                .createdAt(entity.getCreatedAt())
                .createdBy(entity.getCreatedBy())
                .deletedAt(entity.getDeletedAt())
                .deletedBy(entity.getDeletedBy())
                .build();
    }

    private SuperUserEntity toEntity(SuperUser superUser) {
        return SuperUserEntity.builder()
                .id(superUser.getId())
                .idUser(superUser.getIdUser())
                .createdBy(superUser.getCreatedBy())
                .deletedAt(superUser.getDeletedAt())
                .deletedBy(superUser.getDeletedBy())
                .build();
    }
}
