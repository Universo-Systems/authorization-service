package com.universosystems.authorization.features.appstatuses.application;

import com.universosystems.authorization.features.appstatuses.api.requests.AppStatusRequest;
import com.universosystems.authorization.features.appstatuses.api.responses.AppStatusResponse;
import com.universosystems.authorization.features.appstatuses.domain.models.AppStatus;
import org.springframework.stereotype.Component;

@Component
public class AppStatusMapper {
    public AppStatus toDomain(AppStatusRequest request) {
        return AppStatus.builder()
                .name(request.getName())
                .description(request.getDescription())
                .isActive(request.getIsActive())
                .build();
    }

    public AppStatusResponse toResponse(AppStatus appStatus) {
        return new AppStatusResponse(
                appStatus.getId(),
                appStatus.getName(),
                appStatus.getDescription(),
                appStatus.getIsActive(),
                appStatus.getCreatedAt(),
                appStatus.getUpdatedAt());
    }
}
