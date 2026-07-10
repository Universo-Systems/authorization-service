INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, 'identity.users.create', 'Crear usuarios', 'Permite crear usuarios y perfiles'
FROM apps
WHERE apps.code = 'ADMIN_US'
  AND NOT EXISTS (
      SELECT 1
      FROM permissions
      WHERE permissions.id_app = apps.id
        AND permissions.code = 'identity.users.create'
  );

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles
JOIN apps ON apps.id = roles.id_app
JOIN permissions ON permissions.id_app = apps.id
WHERE apps.code = 'ADMIN_US'
  AND roles.code = 'SUPER_USUARIO'
  AND permissions.code = 'identity.users.create'
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );
