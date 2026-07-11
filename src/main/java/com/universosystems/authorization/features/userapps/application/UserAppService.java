package com.universosystems.authorization.features.userapps.application;

import com.universosystems.authorization.features.apps.domain.models.App;
import com.universosystems.authorization.features.apps.domain.repositories.AppRepository;
import com.universosystems.authorization.features.roles.domain.models.Role;
import com.universosystems.authorization.features.roles.domain.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserAppService {
    private final AppRepository appRepository;
    private final RoleRepository roleRepository;

    public List<App> findApps(Integer idUser) {
        return appRepository.findByUserId(idUser);
    }

    public List<Role> findRoles(Integer idUser, String appCode) {
        return roleRepository.findByUserIdAndAppCode(idUser, appCode);
    }
}
