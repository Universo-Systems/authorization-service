package com.universosystems.authorization.features.userroles.infrastructure.clients;

import com.universosystems.authorization.features.userroles.application.ports.IdentityUserClient;
import com.universosystems.authorization.shared.domain.exceptions.DomainException;
import com.universosystems.authorization.shared.domain.exceptions.ExternalServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Component
@RequiredArgsConstructor
public class HttpIdentityUserClient implements IdentityUserClient {
    private final RestClient identityRestClient;

    @Value("${app.identity-service.user-validation-enabled:true}")
    private boolean validationEnabled;

    @Value("${app.identity-service.service-token:}")
    private String serviceToken;

    @Override
    public void ensureUserCanReceiveRole(Integer idUser) {
        if (!validationEnabled) {
            return;
        }

        try {
            IdentityUserResponse user = identityRestClient.get()
                    .uri("/api/users/{idUser}", idUser)
                    .headers(headers -> bearerToken().ifPresent(headers::setBearerAuth))
                    .retrieve()
                    .body(IdentityUserResponse.class);

            if (user == null || user.id() == null) {
                throw new DomainException("Usuario no encontrado en identity-service");
            }
            if (!Boolean.TRUE.equals(user.active())) {
                throw new DomainException("El usuario no esta activo");
            }
            if (!Boolean.TRUE.equals(user.emailVerified())) {
                throw new DomainException("El usuario no tiene el correo verificado");
            }
        } catch (HttpClientErrorException.NotFound exception) {
            throw new DomainException("Usuario no encontrado en identity-service");
        } catch (HttpClientErrorException.Unauthorized | HttpClientErrorException.Forbidden exception) {
            throw new ExternalServiceException("No fue posible autorizar la validacion contra identity-service", exception);
        } catch (RestClientException exception) {
            throw new ExternalServiceException("No fue posible validar el usuario contra identity-service", exception);
        }
    }

    private java.util.Optional<String> bearerToken() {
        if (StringUtils.hasText(serviceToken)) {
            return java.util.Optional.of(serviceToken);
        }

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof JwtAuthenticationToken jwtAuthenticationToken) {
            return java.util.Optional.of(jwtAuthenticationToken.getToken().getTokenValue());
        }
        return java.util.Optional.empty();
    }
}
