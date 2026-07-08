package com.universosystems.authorization.features.appstatuses.application;

import com.universosystems.authorization.features.appstatuses.domain.models.AppStatus;
import com.universosystems.authorization.features.appstatuses.domain.repositories.AppStatusRepository;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppStatusService {
    private final AppStatusRepository repository;

    public List<AppStatus> findAll() {
        return repository.findAll();
    }

    public AppStatus findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estado de app no encontrado"));
    }

    public AppStatus create(AppStatus appStatus) {
        validateDuplicate(null, appStatus.getName());
        return repository.save(appStatus);
    }

    public AppStatus update(Integer id, AppStatus appStatus) {
        AppStatus current = findById(id);
        validateDuplicate(id, appStatus.getName());
        current.setName(appStatus.getName());
        current.setDescription(appStatus.getDescription());
        current.setIsActive(appStatus.getIsActive());
        return repository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void validateDuplicate(Integer id, String name) {
        repository.findByName(name)
                .filter(existing -> id == null || !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DomainException("Ya existe un estado de app con ese nombre");
                });
    }
}
