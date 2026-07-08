package com.universosystems.authorization.features.apps.application;

import com.universosystems.authorization.features.appstatuses.application.AppStatusService;
import com.universosystems.authorization.features.apps.domain.models.App;
import com.universosystems.authorization.features.apps.domain.repositories.AppRepository;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {
    private final AppRepository repository;
    private final AppStatusService appStatusService;

    public List<App> findAll() {
        return repository.findAll();
    }

    public App findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("App no encontrada"));
    }

    public App findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new NotFoundException("App no encontrada"));
    }

    public App create(App app) {
        validateStatus(app.getIdStatus());
        validateDuplicate(null, app.getCode());
        return repository.save(app);
    }

    public App update(Integer id, App app) {
        App current = findById(id);
        validateStatus(app.getIdStatus());
        validateDuplicate(id, app.getCode());
        current.setIdStatus(app.getIdStatus());
        current.setCode(app.getCode());
        current.setName(app.getName());
        current.setDescription(app.getDescription());
        return repository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void validateStatus(Integer idStatus) {
        var status = appStatusService.findById(idStatus);
        if (!Boolean.TRUE.equals(status.getIsActive())) {
            throw new DomainException("El estado de app no esta activo");
        }
    }

    private void validateDuplicate(Integer id, String code) {
        repository.findByCode(code)
                .filter(existing -> id == null || !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DomainException("Ya existe una app con ese codigo");
                });
    }
}
