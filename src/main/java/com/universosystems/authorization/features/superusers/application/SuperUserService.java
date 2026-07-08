package com.universosystems.authorization.features.superusers.application;

import com.universosystems.authorization.features.superusers.domain.models.SuperUser;
import com.universosystems.authorization.features.superusers.domain.repositories.SuperUserRepository;
import com.universosystems.authorization.features.userroles.application.ports.IdentityUserClient;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperUserService {
    private final SuperUserRepository repository;
    private final IdentityUserClient identityUserClient;

    public List<SuperUser> findAll(Integer idUser) {
        return idUser == null ? repository.findAll() : repository.findByUserId(idUser);
    }

    public SuperUser findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Superusuario no encontrado"));
    }

    public boolean isSuperUser(Integer idUser) {
        return idUser != null && repository.findActiveByUserId(idUser).isPresent();
    }

    public SuperUser create(SuperUser superUser) {
        identityUserClient.ensureUserCanReceiveRole(superUser.getIdUser());

        repository.findActiveByUserId(superUser.getIdUser())
                .ifPresent(existing -> {
                    throw new DomainException("El usuario ya es superusuario");
                });

        return repository.findAnyByUserId(superUser.getIdUser())
                .map(existing -> {
                    existing.setDeletedAt(null);
                    existing.setDeletedBy(null);
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(superUser));
    }

    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }
}
