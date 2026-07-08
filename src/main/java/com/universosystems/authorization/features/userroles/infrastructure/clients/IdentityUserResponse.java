package com.universosystems.authorization.features.userroles.infrastructure.clients;

public record IdentityUserResponse(
        Integer id,
        String email,
        Boolean active,
        Boolean emailVerified) {
}
