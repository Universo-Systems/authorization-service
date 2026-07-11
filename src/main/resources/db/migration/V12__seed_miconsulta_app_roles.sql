INSERT INTO apps (id, id_status, code, name, description)
OVERRIDING SYSTEM VALUE
SELECT 100, app_statuses.id, 'MI_CONSULTA', 'MiConsulta', 'Sistema de consultas y atencion a pacientes'
FROM app_statuses
WHERE app_statuses.name = 'ACTIVE'
  AND NOT EXISTS (
      SELECT 1
      FROM apps
      WHERE apps.code = 'MI_CONSULTA'
  );

INSERT INTO roles (id, id_app, id_status, code, name, description)
OVERRIDING SYSTEM VALUE
SELECT seed.id, apps.id, role_statuses.id, seed.code, seed.name, seed.description
FROM apps
CROSS JOIN role_statuses
CROSS JOIN (
    VALUES
        (1000, 'PROFESIONISTA', 'Profesionista', 'Profesional de la salud que atiende pacientes'),
        (1001, 'PACIENTE', 'Paciente', 'Usuario que agenda y recibe atencion'),
        (1002, 'ASISTENTE', 'Asistente', 'Usuario que apoya la operacion de agenda y pacientes'),
        (1003, 'ADMINISTRADOR_CLINICA', 'Administrador de clinica', 'Usuario que administra una clinica u organizacion')
) AS seed(id, code, name, description)
WHERE apps.code = 'MI_CONSULTA'
  AND role_statuses.name = 'ACTIVE'
  AND NOT EXISTS (
      SELECT 1
      FROM roles
      WHERE roles.id_app = apps.id
        AND roles.code = seed.code
  );

INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, seed.code, seed.name, seed.description
FROM apps
CROSS JOIN (
    VALUES
        ('miconsulta.patients.view', 'Ver pacientes', 'Consultar pacientes'),
        ('miconsulta.patients.create', 'Crear pacientes', 'Crear pacientes'),
        ('miconsulta.patients.update', 'Editar pacientes', 'Editar pacientes'),
        ('miconsulta.patients.delete', 'Eliminar pacientes', 'Eliminar pacientes'),
        ('miconsulta.appointments.view', 'Ver citas', 'Consultar citas'),
        ('miconsulta.appointments.create', 'Crear citas', 'Crear citas'),
        ('miconsulta.appointments.update', 'Editar citas', 'Editar citas'),
        ('miconsulta.appointments.cancel', 'Cancelar citas', 'Cancelar citas'),
        ('miconsulta.clinical_records.view', 'Ver expedientes', 'Consultar expedientes clinicos'),
        ('miconsulta.clinical_records.update', 'Editar expedientes', 'Editar expedientes clinicos'),
        ('miconsulta.billing.view', 'Ver facturacion', 'Consultar facturacion y pagos'),
        ('miconsulta.settings.manage', 'Administrar configuracion', 'Administrar configuracion de MiConsulta')
) AS seed(code, name, description)
WHERE apps.code = 'MI_CONSULTA'
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
WHERE apps.code = 'MI_CONSULTA'
  AND roles.code = 'ADMINISTRADOR_CLINICA'
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles
JOIN apps ON apps.id = roles.id_app
JOIN permissions ON permissions.id_app = apps.id
WHERE apps.code = 'MI_CONSULTA'
  AND roles.code = 'PROFESIONISTA'
  AND permissions.code IN (
      'miconsulta.patients.view',
      'miconsulta.patients.create',
      'miconsulta.patients.update',
      'miconsulta.appointments.view',
      'miconsulta.appointments.create',
      'miconsulta.appointments.update',
      'miconsulta.appointments.cancel',
      'miconsulta.clinical_records.view',
      'miconsulta.clinical_records.update',
      'miconsulta.billing.view'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles
JOIN apps ON apps.id = roles.id_app
JOIN permissions ON permissions.id_app = apps.id
WHERE apps.code = 'MI_CONSULTA'
  AND roles.code = 'ASISTENTE'
  AND permissions.code IN (
      'miconsulta.patients.view',
      'miconsulta.patients.create',
      'miconsulta.patients.update',
      'miconsulta.appointments.view',
      'miconsulta.appointments.create',
      'miconsulta.appointments.update',
      'miconsulta.appointments.cancel'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles
JOIN apps ON apps.id = roles.id_app
JOIN permissions ON permissions.id_app = apps.id
WHERE apps.code = 'MI_CONSULTA'
  AND roles.code = 'PACIENTE'
  AND permissions.code IN (
      'miconsulta.appointments.view',
      'miconsulta.appointments.create',
      'miconsulta.appointments.cancel',
      'miconsulta.clinical_records.view'
  )
  AND NOT EXISTS (
      SELECT 1
      FROM role_permissions
      WHERE role_permissions.id_role = roles.id
        AND role_permissions.id_permission = permissions.id
  );
