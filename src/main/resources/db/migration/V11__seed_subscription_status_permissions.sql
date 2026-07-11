INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, seed.code, seed.name, seed.description
FROM apps CROSS JOIN (VALUES
 ('subscription.subscription_statuses.view','Ver estados de suscripción','Consultar estados de suscripción'),
 ('subscription.subscription_statuses.create','Crear estados de suscripción','Crear estados de suscripción'),
 ('subscription.subscription_statuses.update','Editar estados de suscripción','Editar estados de suscripción'),
 ('subscription.subscription_statuses.delete','Eliminar estados de suscripción','Eliminar estados de suscripción')
) AS seed(code,name,description)
WHERE apps.code='ADMIN_US'
AND NOT EXISTS (SELECT 1 FROM permissions p WHERE p.id_app=apps.id AND p.code=seed.code);

INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles JOIN apps ON apps.id=roles.id_app JOIN permissions ON permissions.id_app=apps.id
WHERE apps.code='ADMIN_US' AND roles.code='SUPER_USUARIO'
  AND permissions.code LIKE 'subscription.subscription_statuses.%'
  AND NOT EXISTS (SELECT 1 FROM role_permissions rp
                  WHERE rp.id_role=roles.id AND rp.id_permission=permissions.id);
