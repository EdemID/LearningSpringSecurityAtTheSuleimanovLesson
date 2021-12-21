-- Table: users
CREATE TABLE users (
id serial primary key ,
username VARCHAR(255) not null,
-- Используется кодировка bcrypt для пароля
password VARCHAR(255) not null
);

-- Table: roles
CREATE TABLE roles(
id serial primary key ,
name varchar(100) not null
);

-- Table for mapping user and roles: user_roles
CREATE TABLE user_roles(
    user_id int not null ,
    role_id int not null ,
    -- Внешний ключ user_id, который ссылается на таблицу users
    FOREIGN KEY (user_id) REFERENCES users(id),
    -- Внешний ключ role_id, который ссылается на таблицу roles
    FOREIGN KEY (role_id) REFERENCES roles(id),
    -- Сделать уникальными пару ключей
    UNIQUE (user_id, role_id)
);

-- Insert data
INSERT INTO users VALUES (1, 'root', '$2a$11$uSXS6rLJ91WjgOHhEGDx..VGs7MkKZV68Lv5r1uwFu7HgtRn3dcXG');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');
-- пользователь с id 1 имеет роль user
INSERT INTO user_roles VALUES (1, 2)