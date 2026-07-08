package com.universosystems.authorization.features.userroles.application;

import com.universosystems.authorization.features.userroles.api.requests.UserRoleRequest;
import com.universosystems.authorization.features.userroles.api.responses.UserRoleResponse;
import com.universosystems.authorization.features.userroles.domain.models.UserRole;
import org.springframework.stereotype.Component;

@Component
public class UserRoleMapper {
    public UserRole toDomain(UserRoleRequest request) {
        return UserRole.builder()
                .idUser(request.getIdUser())
                .idRole(request.getIdRole())
                .build();
    }

    public UserRoleResponse toResponse(UserRole userRole) {
        return new UserRoleResponse(
                userRole.getId(),
                userRole.getIdUser(),
                userRole.getIdRole(),
                userRole.getRoleCode(),
                userRole.getAppCode(),
                userRole.getCreatedAt());
    }
}
