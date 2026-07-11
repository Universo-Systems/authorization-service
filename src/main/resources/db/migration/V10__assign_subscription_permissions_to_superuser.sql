INSERT INTO role_permissions (id_role, id_permission)
SELECT roles.id, permissions.id
FROM roles JOIN apps ON apps.id=roles.id_app JOIN permissions ON permissions.id_app=apps.id
WHERE apps.code='ADMIN_US' AND roles.code='SUPER_USUARIO' AND permissions.code LIKE 'subscription.%'
AND NOT EXISTS (SELECT 1 FROM role_permissions rp WHERE rp.id_role=roles.id AND rp.id_permission=permissions.id);
