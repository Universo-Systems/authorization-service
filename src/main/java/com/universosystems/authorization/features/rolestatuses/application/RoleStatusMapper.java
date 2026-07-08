package com.universosystems.authorization.features.rolestatuses.application;

import com.universosystems.authorization.features.rolestatuses.api.requests.RoleStatusRequest;
import com.universosystems.authorization.features.rolestatuses.api.responses.RoleStatusResponse;
import com.universosystems.authorization.features.rolestatuses.domain.models.RoleStatus;
import org.springframework.stereotype.Component;

@Component
public class RoleStatusMapper {
    public RoleStatus toDomain(RoleStatusRequest request) {
        return RoleStatus.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();
    }

    public RoleStatusResponse toResponse(RoleStatus roleStatus) {
        return new RoleStatusResponse(
                roleStatus.getId(),
                roleStatus.getName(),
                roleStatus.getDescription(),
                roleStatus.getIsActive(),
                roleStatus.getCreatedAt(),
                roleStatus.getUpdatedAt());
    }
}
