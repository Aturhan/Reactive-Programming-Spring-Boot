CREATE TABLE IF NOT EXISTS users (
    id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(250),
    title  VARCHAR(250),
    email VARCHAR(250) UNIQUE,
    password VARCHAR(250),
    interest VARCHAR(250),
    role VARCHAR(250),
    created_at  DATE,
    is_active BOOLEAN DEFAULT true
);


CREATE TABLE IF NOT EXISTS firms (
    firm_id UUID NOT NULL PRIMARY KEY,
    name VARCHAR(250) UNIQUE,
    employee_count INT,
    category VARCHAR(250),
    is_active BOOLEAN DEFAULT true,
    email VARCHAR(250),
    creation_date DATE
);