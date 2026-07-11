package com.universosystems.authorization.features.userapps.api.controllers;

import com.universosystems.authorization.features.apps.api.responses.AppResponse;
import com.universosystems.authorization.features.apps.application.AppMapper;
import com.universosystems.authorization.features.roles.api.responses.RoleResponse;
import com.universosystems.authorization.features.roles.application.RoleMapper;
import com.universosystems.authorization.features.userapps.application.UserAppService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/user-apps")
@RequiredArgsConstructor
public class UserAppController {
    private final UserAppService service;
    private final AppMapper appMapper;
    private final RoleMapper roleMapper;

    @GetMapping
    public List<AppResponse> obtenerApps(
            @RequestParam(required = false) Integer idUser,
            @AuthenticationPrincipal Jwt jwt) {
        return service.findApps(resolveUserId(idUser, jwt)).stream()
                .map(appMapper::toResponse)
                .toList();
    }

    @GetMapping("/{appCode}/roles")
    public List<RoleResponse> obtenerRoles(
            @PathVariable String appCode,
            @RequestParam(required = false) Integer idUser,
            @AuthenticationPrincipal Jwt jwt) {
        return service.findRoles(resolveUserId(idUser, jwt), appCode).stream()
                .map(roleMapper::toResponse)
                .toList();
    }

    private Integer resolveUserId(Integer idUser, Jwt jwt) {
        if (idUser != null) {
            return idUser;
        }
        Object claim = jwt.getClaim("idUser");
        if (claim instanceof Number number) {
            return number.intValue();
        }
        if (claim instanceof String value && !value.isBlank()) {
            return Integer.valueOf(value);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No se pudo resolver el usuario autenticado");
    }
}
