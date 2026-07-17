package com.universosystems.authorization.features.roles.application;

import com.universosystems.authorization.features.apps.application.AppService;
import com.universosystems.authorization.features.roles.domain.models.Role;
import com.universosystems.authorization.features.roles.domain.repositories.RoleRepository;
import com.universosystems.authorization.features.rolestatuses.application.RoleStatusService;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository repository;
    private final AppService appService;
    private final RoleStatusService roleStatusService;

    public List<Role> findAll(Integer idApp) {
        return idApp == null ? repository.findAll() : repository.findByAppId(idApp);
    }

    public List<Role> findActiveByAppId(Integer idApp) {
        return repository.findActiveByAppId(idApp);
    }

    public List<Role> findByUserIdAndAppCode(Integer idUser, String appCode) {
        return repository.findByUserIdAndAppCode(idUser, appCode);
    }

    public Role findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol no encontrado"));
    }

    public Role create(Role role) {
        appService.findById(role.getIdApp());
        validateStatus(role.getIdStatus());
        validateDuplicate(null, role);
        return repository.save(role);
    }

    public Role update(Integer id, Role role) {
        Role current = findById(id);
        appService.findById(role.getIdApp());
        validateStatus(role.getIdStatus());
        validateDuplicate(id, role);
        current.setIdApp(role.getIdApp());
        current.setIdStatus(role.getIdStatus());
        current.setCode(role.getCode());
        current.setName(role.getName());
        current.setDescription(role.getDescription());
        return repository.save(current);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void validateDuplicate(Integer id, Role role) {
        repository.findByAppIdAndCode(role.getIdApp(), role.getCode())
                .filter(existing -> id == null || !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DomainException("Ya existe un rol con ese codigo para la app");
                });
    }

    private void validateStatus(Integer idStatus) {
        var status = roleStatusService.findById(idStatus);
        if (!Boolean.TRUE.equals(status.getIsActive())) {
            throw new DomainException("El estado de rol no esta activo");
        }
    }
}
