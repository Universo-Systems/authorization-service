package com.universosystems.authorization.features.permissions.application;

import com.universosystems.authorization.features.apps.application.AppService;
import com.universosystems.authorization.features.permissions.domain.models.Permission;
import com.universosystems.authorization.features.permissions.domain.repositories.PermissionRepository;
import com.universosystems.authorization.features.superusers.application.SuperUserService;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionRepository repository;
    private final AppService appService;
    private final SuperUserService superUserService;

    public List<Permission> findAll(Integer idApp) {
        return idApp == null ? repository.findAll() : repository.findByAppId(idApp);
    }

    public List<Permission> findEffectiveByUserIdAndAppCode(Integer idUser, String appCode) {
        appService.findByCode(appCode);
        if (superUserService.isSuperUser(idUser)) {
            return repository.findByAppCode(appCode);
        }
        return repository.findEffectiveByUserIdAndAppCode(idUser, appCode);
    }

    public Permission findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permiso no encontrado"));
    }

    public Permission create(Permission permission) {
        appService.findById(permission.getIdApp());
        validateDuplicate(null, permission);
        return repository.save(permission);
    }

    public Permission update(Integer id, Permission permission) {
        Permission current = findById(id);
        appService.findById(permission.getIdApp());
        validateDuplicate(id, permission);
        current.setIdApp(permission.getIdApp());
        current.setCode(permission.getCode());
        current.setName(permission.getName());
        current.setDescription(permission.getDescription());
        return repository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void validateDuplicate(Integer id, Permission permission) {
        repository.findByAppIdAndCode(permission.getIdApp(), permission.getCode())
                .filter(existing -> id == null || !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DomainException("Ya existe un permiso con ese codigo para la app");
                });
    }
}
