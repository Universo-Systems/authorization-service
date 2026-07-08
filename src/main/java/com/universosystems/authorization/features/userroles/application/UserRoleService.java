package com.universosystems.authorization.features.userroles.application;

import com.universosystems.authorization.features.roles.application.RoleService;
import com.universosystems.authorization.features.userroles.application.ports.IdentityUserClient;
import com.universosystems.authorization.features.userroles.domain.models.UserRole;
import com.universosystems.authorization.features.userroles.domain.repositories.UserRoleRepository;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {
    private final UserRoleRepository repository;
    private final RoleService roleService;
    private final IdentityUserClient identityUserClient;

    public List<UserRole> findAll(Integer idUser, Integer idRole) {
        if (idUser != null) {
            return repository.findByUserId(idUser);
        }
        if (idRole != null) {
            return repository.findByRoleId(idRole);
        }
        return repository.findAll();
    }

    public UserRole findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol de usuario no encontrado"));
    }

    public UserRole create(UserRole userRole) {
        identityUserClient.ensureUserCanReceiveRole(userRole.getIdUser());
        roleService.findById(userRole.getIdRole());

        repository.findActiveByUserIdAndRoleId(userRole.getIdUser(), userRole.getIdRole())
                .ifPresent(existing -> {
                    throw new DomainException("El usuario ya tiene asignado ese rol");
                });

        return repository.findAnyByUserIdAndRoleId(userRole.getIdUser(), userRole.getIdRole())
                .map(existing -> {
                    existing.setDeletedAt(null);
                    existing.setDeletedBy(null);
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(userRole));
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
