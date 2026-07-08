package com.universosystems.authorization.features.superusers.domain.repositories;

import com.universosystems.authorization.features.superusers.domain.models.SuperUser;

import java.util.List;
import java.util.Optional;

public interface SuperUserRepository {
    List<SuperUser> findAll();

    List<SuperUser> findByUserId(Integer idUser);

    Optional<SuperUser> findById(Integer id);

    Optional<SuperUser> findActiveByUserId(Integer idUser);

    Optional<SuperUser> findAnyByUserId(Integer idUser);

    SuperUser save(SuperUser superUser);

    void deleteById(Integer id);
}
