INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, seed.code, seed.name, seed.description
FROM apps
CROSS JOIN (
    VALUES
        ('authorization.role_permissions.view', 'Ver permisos de roles', 'Permite consultar permisos asignados a roles'),
        ('authorization.role_permissions.create', 'Asignar permisos a roles', 'Permite asignar permisos a roles'),
        ('authorization.role_permissions.delete', 'Quitar permisos a roles', 'Permite quitar permisos asignados a roles')
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
      'authorization.role_permissions.view',
      'authorization.role_permissions.create',
      'authorization.role_permissions.delete'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );
