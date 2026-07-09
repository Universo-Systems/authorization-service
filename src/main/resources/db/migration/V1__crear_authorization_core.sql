CREATE TABLE app_statuses (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(250),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER,
    CONSTRAINT uk_app_statuses_name UNIQUE (name)
);

INSERT INTO app_statuses (name, description)
VALUES
    ('ACTIVE', 'Aplicacion activa'),
    ('DISABLED', 'Aplicacion deshabilitada');

CREATE TABLE apps (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_status INTEGER NOT NULL,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(250),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by INTEGER,
    CONSTRAINT fk_apps_app_statuses FOREIGN KEY (id_status) REFERENCES app_statuses(id),
    CONSTRAINT uk_apps_code UNIQUE (code)
);

CREATE INDEX ix_apps_id_status ON apps(id_status);

CREATE TABLE role_statuses (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(60) NOT NULL,
    description VARCHAR(250),
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER,
    CONSTRAINT uk_role_statuses_name UNIQUE (name)
);

INSERT INTO role_statuses (name, description)
VALUES
    ('ACTIVE', 'Rol activo'),
    ('DISABLED', 'Rol deshabilitado');

CREATE TABLE roles (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_app INTEGER NOT NULL,
    id_status INTEGER NOT NULL,
    code VARCHAR(80) NOT NULL,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(250),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by INTEGER,
    CONSTRAINT fk_roles_apps FOREIGN KEY (id_app) REFERENCES apps(id),
    CONSTRAINT fk_roles_role_statuses FOREIGN KEY (id_status) REFERENCES role_statuses(id),
    CONSTRAINT uk_roles_app_code UNIQUE (id_app, code)
);

CREATE INDEX ix_roles_id_app ON roles(id_app);
CREATE INDEX ix_roles_id_status ON roles(id_status);

CREATE TABLE permissions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_app INTEGER NOT NULL,
    code VARCHAR(120) NOT NULL,
    name VARCHAR(120) NOT NULL,
    description VARCHAR(250),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by INTEGER,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by INTEGER,
    CONSTRAINT fk_permissions_apps FOREIGN KEY (id_app) REFERENCES apps(id),
    CONSTRAINT uk_permissions_app_code UNIQUE (id_app, code)
);

CREATE INDEX ix_permissions_id_app ON permissions(id_app);

CREATE TABLE role_permissions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_role INTEGER NOT NULL,
    id_permission INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    CONSTRAINT fk_role_permissions_roles FOREIGN KEY (id_role) REFERENCES roles(id),
    CONSTRAINT fk_role_permissions_permissions FOREIGN KEY (id_permission) REFERENCES permissions(id),
    CONSTRAINT uk_role_permissions_role_permission UNIQUE (id_role, id_permission)
);

CREATE INDEX ix_role_permissions_id_role ON role_permissions(id_role);
CREATE INDEX ix_role_permissions_id_permission ON role_permissions(id_permission);

CREATE TABLE super_users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_user INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by INTEGER,
    CONSTRAINT uk_super_users_user UNIQUE (id_user)
);

CREATE INDEX ix_super_users_id_user ON super_users(id_user);

CREATE TABLE user_roles (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    id_user INTEGER NOT NULL,
    id_role INTEGER NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by INTEGER,
    deleted_at TIMESTAMP WITH TIME ZONE,
    deleted_by INTEGER,
    CONSTRAINT fk_user_roles_roles FOREIGN KEY (id_role) REFERENCES roles(id),
    CONSTRAINT uk_user_roles_user_role UNIQUE (id_user, id_role)
);

CREATE INDEX ix_user_roles_id_user ON user_roles(id_user);
CREATE INDEX ix_user_roles_id_role ON user_roles(id_role);
