CREATE TABLE tb_users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'ROLE_USER',
    active BOOLEAN NOT NULL DEFAULT TRUE,
    last_login TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO tb_users (name, email, password, role, active, created_at)
VALUES (
    'admin',
    'admin',
    '$2b$12$nqNij/u0HSDDlernW/JDD.TcVX5nkc3Isf5Txh91rMDPSobOzJL.O', -- senha "admin" encriptada
    '0',
    TRUE,
    NOW()
);