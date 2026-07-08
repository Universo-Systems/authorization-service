package com.universosystems.authorization.features.rolepermissions.application;

import com.universosystems.authorization.features.permissions.application.PermissionService;
import com.universosystems.authorization.features.rolepermissions.domain.models.RolePermission;
import com.universosystems.authorization.features.rolepermissions.domain.repositories.RolePermissionRepository;
import com.universosystems.authorization.features.roles.application.RoleService;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RolePermissionService {
    private final RolePermissionRepository repository;
    private final RoleService roleService;
    private final PermissionService permissionService;

    public List<RolePermission> findAll(Integer idRole) {
        return idRole == null ? repository.findAll() : repository.findByRoleId(idRole);
    }

    public RolePermission findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permiso del rol no encontrado"));
    }

    public RolePermission create(RolePermission rolePermission) {
        var role = roleService.findById(rolePermission.getIdRole());
        var permission = permissionService.findById(rolePermission.getIdPermission());
        if (!role.getIdApp().equals(permission.getIdApp())) {
            throw new DomainException("El rol y el permiso deben pertenecer a la misma app");
        }
        repository.findByRoleIdAndPermissionId(rolePermission.getIdRole(), rolePermission.getIdPermission())
                .ifPresent(existing -> {
                    throw new DomainException("El rol ya tiene asignado ese permiso");
                });
        return repository.save(rolePermission);
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
