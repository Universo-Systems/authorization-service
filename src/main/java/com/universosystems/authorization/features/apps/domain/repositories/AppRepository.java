package com.universosystems.authorization.features.apps.domain.repositories;

import com.universosystems.authorization.features.apps.domain.models.App;

import java.util.List;
import java.util.Optional;

public interface AppRepository {
    List<App> findAll();

    List<App> findByUserId(Integer idUser);

    Optional<App> findById(Integer id);

    Optional<App> findByCode(String code);

    App save(App app);

    void deleteById(Integer id);
}
