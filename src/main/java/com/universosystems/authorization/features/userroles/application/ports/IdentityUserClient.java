package com.universosystems.authorization.features.userroles.application.ports;

public interface IdentityUserClient {
    void ensureUserCanReceiveRole(Integer idUser);
}
