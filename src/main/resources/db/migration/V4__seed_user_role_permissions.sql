INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, seed.code, seed.name, seed.description
FROM apps
CROSS JOIN (
    VALUES
        ('authorization.user_roles.view', 'Ver roles de usuarios', 'Permite consultar roles asignados a usuarios'),
        ('authorization.user_roles.create', 'Asignar roles a usuarios', 'Permite asignar roles a usuarios'),
        ('authorization.user_roles.delete', 'Quitar roles a usuarios', 'Permite quitar roles asignados a usuarios')
) AS seed(code, name, description)
WHERE apps.code = 'ADMIN_US'
  AND NOT EXISTS (
      SELECT 1
      FROM permissions
      WHERE permissions.id_app = apps.id
        AND permissions.code = seed.code
  );

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles
JOIN apps ON apps.id = roles.id_app
JOIN permissions ON permissions.id_app = apps.id
WHERE apps.code = 'ADMIN_US'
  AND roles.code = 'SUPER_USUARIO'
  AND permissions.code IN (
      'authorization.user_roles.view',
      'authorization.user_roles.create',
      'authorization.user_roles.delete'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );
