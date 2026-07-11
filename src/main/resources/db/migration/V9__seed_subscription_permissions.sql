INSERT INTO permissions (id_app, code, name, description)
SELECT apps.id, seed.code, seed.name, seed.description
FROM apps CROSS JOIN (VALUES
 ('subscription.plans.view','Ver planes','Consultar planes'),
 ('subscription.plans.create','Crear planes','Crear planes'),
 ('subscription.plans.update','Editar planes','Editar planes'),
 ('subscription.plans.delete','Eliminar planes','Eliminar planes'),
 ('subscription.plan_entitlements.view','Ver funcionalidades de planes','Consultar funcionalidades de planes'),
 ('subscription.plan_entitlements.create','Crear funcionalidades de planes','Crear funcionalidades de planes'),
 ('subscription.plan_entitlements.update','Editar funcionalidades de planes','Editar funcionalidades de planes'),
 ('subscription.plan_entitlements.delete','Eliminar funcionalidades de planes','Eliminar funcionalidades de planes'),
 ('subscription.subscriptions.view','Ver suscripciones','Consultar suscripciones'),
 ('subscription.subscriptions.create','Crear suscripciones','Crear suscripciones'),
 ('subscription.subscriptions.update','Editar suscripciones','Editar suscripciones'),
 ('subscription.subscriptions.delete','Eliminar suscripciones','Eliminar suscripciones'),
 ('subscription.events.view','Ver eventos de suscripción','Consultar eventos de suscripción')
) AS seed(code,name,description)
WHERE apps.code='ADMIN_US'
AND NOT EXISTS (SELECT 1 FROM permissions p WHERE p.id_app=apps.id AND p.code=seed.code);
