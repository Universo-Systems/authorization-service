package com.universosystems.authorization.features.apps.application;

import com.universosystems.authorization.features.apps.api.requests.AppRequest;
import com.universosystems.authorization.features.apps.api.responses.AppResponse;
import com.universosystems.authorization.features.apps.domain.models.App;
import org.springframework.stereotype.Component;

@Component
public class AppMapper {
    public App toDomain(AppRequest request) {
        return App.builder()
                .idStatus(request.getIdStatus())
                .code(normalizeCode(request.getCode()))
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public AppResponse toResponse(App app) {
        return new AppResponse(
                app.getId(),
                app.getIdStatus(),
                app.getStatusName(),
                app.getCode(),
                app.getName(),
                app.getDescription(),
                app.getCreatedAt(),
                app.getUpdatedAt());
    }

    private String normalizeCode(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }
}
