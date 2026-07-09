INSERT INTO apps (id_status, code, name, description)
SELECT app_statuses.id, 'ADMIN_US', 'Admin US', 'Administrador de Universo Systems'
FROM app_statuses
WHERE app_statuses.name = 'ACTIVE'
  AND NOT EXISTS (
      SELECT 1
      FROM apps
      WHERE apps.code = 'ADMIN_US'
  );

INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, seed.code, seed.name, seed.description
FROM apps
CROSS JOIN (
    VALUES
        ('identity.users.view', 'Ver usuarios', 'Permite consultar usuarios de identity-service'),
        ('identity.users.update', 'Editar usuarios', 'Permite editar usuarios y perfiles'),
        ('identity.users.reset_password', 'Restablecer passwords', 'Permite asignar un nuevo password a usuarios'),
        ('identity.users.delete', 'Eliminar usuarios', 'Permite eliminar usuarios'),
        ('authorization.apps.view', 'Ver apps', 'Permite consultar apps'),
        ('authorization.apps.create', 'Crear apps', 'Permite crear apps'),
        ('authorization.apps.update', 'Editar apps', 'Permite editar apps'),
        ('authorization.apps.delete', 'Eliminar apps', 'Permite eliminar apps'),
        ('authorization.roles.view', 'Ver roles', 'Permite consultar roles'),
        ('authorization.roles.create', 'Crear roles', 'Permite crear roles'),
        ('authorization.roles.update', 'Editar roles', 'Permite editar roles'),
        ('authorization.roles.delete', 'Eliminar roles', 'Permite eliminar roles'),
        ('authorization.permissions.view', 'Ver permisos', 'Permite consultar permisos'),
        ('authorization.permissions.create', 'Crear permisos', 'Permite crear permisos'),
        ('authorization.permissions.update', 'Editar permisos', 'Permite editar permisos'),
        ('authorization.permissions.delete', 'Eliminar permisos', 'Permite eliminar permisos')
) AS seed(code, name, description)
WHERE apps.code = 'ADMIN_US'
  AND NOT EXISTS (
      SELECT 1
      FROM permissions
      WHERE permissions.id_app = apps.id
        AND permissions.code = seed.code
  );

INSERT INTO roles (id_app, id_status, code, name, description)
SELECT apps.id, role_statuses.id, 'SUPER_USUARIO', 'Super usuario', 'Acceso total a Admin US'
FROM apps
CROSS JOIN role_statuses
WHERE apps.code = 'ADMIN_US'
  AND role_statuses.name = 'ACTIVE'
  AND NOT EXISTS (
      SELECT 1
      FROM roles
      WHERE roles.id_app = apps.id
        AND roles.code = 'SUPER_USUARIO'
  );

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles
JOIN apps ON apps.id = roles.id_app
JOIN permissions ON permissions.id_app = apps.id
WHERE apps.code = 'ADMIN_US'
  AND roles.code = 'SUPER_USUARIO'
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );

INSERT INTO user_roles (id_user, id_role)
SELECT 1, roles.id
FROM roles
JOIN apps ON apps.id = roles.id_app
WHERE apps.code = 'ADMIN_US'
  AND roles.code = 'SUPER_USUARIO'
  AND NOT EXISTS (
      SELECT 1
      FROM user_roles
      WHERE user_roles.id_user = 1
        AND user_roles.id_role = roles.id
  );
