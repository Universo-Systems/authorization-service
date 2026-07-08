package com.universosystems.authorization.features.superusers.application;

import com.universosystems.authorization.features.superusers.api.requests.SuperUserRequest;
import com.universosystems.authorization.features.superusers.api.responses.SuperUserResponse;
import com.universosystems.authorization.features.superusers.domain.models.SuperUser;
import org.springframework.stereotype.Component;

@Component
public class SuperUserMapper {
    public SuperUser toDomain(SuperUserRequest request) {
        return SuperUser.builder()
                .idUser(request.getIdUser())
                .build();
    }

    public SuperUserResponse toResponse(SuperUser superUser) {
        return new SuperUserResponse(
                superUser.getId(),
                superUser.getIdUser(),
                superUser.getCreatedAt());
    }
}
