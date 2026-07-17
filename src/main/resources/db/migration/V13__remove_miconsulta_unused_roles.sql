DELETE FROM role_permissions
WHERE id_role IN (
    SELECT roles.id
    FROM roles
    JOIN apps ON apps.id = roles.id_app
    WHERE apps.code = 'MI_CONSULTA'
      AND roles.code IN ('ASISTENTE', 'ADMINISTRADOR_CLINICA')
);

DELETE FROM user_roles
WHERE id_role IN (
    SELECT roles.id
    FROM roles
    JOIN apps ON apps.id = roles.id_app
    WHERE apps.code = 'MI_CONSULTA'
      AND roles.code IN ('ASISTENTE', 'ADMINISTRADOR_CLINICA')
);

DELETE FROM roles
WHERE id_app = (SELECT id FROM apps WHERE code = 'MI_CONSULTA')
  AND code IN ('ASISTENTE', 'ADMINISTRADOR_CLINICA');
