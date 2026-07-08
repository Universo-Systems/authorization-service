package com.universosystems.authorization;

import com.universosystems.authorization.features.apps.domain.models.App;
import com.universosystems.authorization.features.apps.application.AppService;
import com.universosystems.authorization.features.appstatuses.application.AppStatusService;
import com.universosystems.authorization.features.permissions.application.PermissionService;
import com.universosystems.authorization.features.permissions.domain.models.Permission;
import com.universosystems.authorization.features.rolepermissions.application.RolePermissionService;
import com.universosystems.authorization.features.rolepermissions.domain.models.RolePermission;
import com.universosystems.authorization.features.roles.application.RoleService;
import com.universosystems.authorization.features.roles.domain.models.Role;
import com.universosystems.authorization.features.rolestatuses.application.RoleStatusService;
import com.universosystems.authorization.features.superusers.application.SuperUserService;
import com.universosystems.authorization.features.superusers.domain.models.SuperUser;
import com.universosystems.authorization.features.userroles.application.UserRoleService;
import com.universosystems.authorization.features.userroles.domain.models.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class AuthorizationServiceApplicationTests {
    @Autowired
    private AppStatusService appStatusService;

    @Autowired
    private AppService appService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleStatusService roleStatusService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private SuperUserService superUserService;

    @Test
    void resolvesEffectivePermissionsForDirectAdminRole() {
        Integer activeStatusId = appStatusService.findAll().stream()
                .filter(status -> "ACTIVE".equals(status.getName()))
                .findFirst()
                .orElseThrow()
                .getId();
        Integer activeRoleStatusId = roleStatusService.findAll().stream()
                .filter(status -> "ACTIVE".equals(status.getName()))
                .findFirst()
                .orElseThrow()
                .getId();

        App app = appService.create(App.builder()
                .idStatus(activeStatusId)
                .code("ADMIN_US")
                .name("Admin-US")
                .description("Administracion de Universo Systems")
                .build());

        Role role = roleService.create(Role.builder()
                .idApp(app.getId())
                .idStatus(activeRoleStatusId)
                .code("ADMIN")
                .name("Admin")
                .description("Administrador de Admin-US")
                .build());

        Permission permission = permissionService.create(Permission.builder()
                .idApp(app.getId())
                .code("users.create")
                .name("Crear usuarios")
                .description("Permite crear usuarios")
                .build());

        rolePermissionService.create(RolePermission.builder()
                .idRole(role.getId())
                .idPermission(permission.getId())
                .build());

        userRoleService.create(UserRole.builder()
                .idUser(100)
                .idRole(role.getId())
                .build());

        assertThat(permissionService.findEffectiveByUserIdAndAppCode(100, "ADMIN_US"))
                .extracting(Permission::getCode)
                .containsExactly("users.create");
    }

    @Test
    void resolvesAllAppPermissionsForSuperUser() {
        Integer activeStatusId = appStatusService.findAll().stream()
                .filter(status -> "ACTIVE".equals(status.getName()))
                .findFirst()
                .orElseThrow()
                .getId();

        App app = appService.create(App.builder()
                .idStatus(activeStatusId)
                .code("SUPER_APP")
                .name("Super App")
                .description("App para validar superusuarios")
                .build());

        permissionService.create(Permission.builder()
                .idApp(app.getId())
                .code("reports.read")
                .name("Leer reportes")
                .build());
        permissionService.create(Permission.builder()
                .idApp(app.getId())
                .code("reports.delete")
                .name("Eliminar reportes")
                .build());

        superUserService.create(SuperUser.builder()
                .idUser(200)
                .build());

        assertThat(permissionService.findEffectiveByUserIdAndAppCode(200, "SUPER_APP"))
                .extracting(Permission::getCode)
                .containsExactly("reports.delete", "reports.read");
    }
}
