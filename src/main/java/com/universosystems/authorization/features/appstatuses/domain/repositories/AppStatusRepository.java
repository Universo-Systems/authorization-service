package com.universosystems.authorization.features.appstatuses.domain.repositories;

import com.universosystems.authorization.features.appstatuses.domain.models.AppStatus;

import java.util.List;
import java.util.Optional;

public interface AppStatusRepository {
    List<AppStatus> findAll();

    Optional<AppStatus> findById(Integer id);

    Optional<AppStatus> findByName(String name);

    AppStatus save(AppStatus appStatus);

    void deleteById(Integer id);
}
