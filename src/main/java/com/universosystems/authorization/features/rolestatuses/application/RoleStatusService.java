package com.universosystems.authorization.features.rolestatuses.application;

import com.universosystems.authorization.features.rolestatuses.domain.models.RoleStatus;
import com.universosystems.authorization.features.rolestatuses.domain.repositories.RoleStatusRepository;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleStatusService {
    private final RoleStatusRepository repository;

    public List<RoleStatus> findAll() {
        return repository.findAll();
    }

    public RoleStatus findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estado de rol no encontrado"));
    }

    public RoleStatus create(RoleStatus roleStatus) {
        validateDuplicate(null, roleStatus.getName());
        return repository.save(roleStatus);
    }

    public RoleStatus update(Integer id, RoleStatus roleStatus) {
        RoleStatus current = findById(id);
        validateDuplicate(id, roleStatus.getName());
        current.setName(roleStatus.getName());
        current.setDescription(roleStatus.getDescription());
        current.setIsActive(roleStatus.getIsActive());
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
                    throw new DomainException("Ya existe un estado de rol con ese nombre");
                });
    }
}
