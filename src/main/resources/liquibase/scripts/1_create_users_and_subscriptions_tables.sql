--liquibase formatted sql

--changeset inushtaev:create_users_and_subscriptions_tables
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users
(
    id          UUID         PRIMARY KEY    DEFAULT uuid_generate_v4(),
    username    TEXT         NOT NULL,
    first_name  TEXT,
    last_name   TEXT,
    middle_name TEXT,
    created_at  TIMESTAMP    NOT NULL       DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL       DEFAULT NOW(),
    deleted_at  TIMESTAMP                   DEFAULT NULL,

    UNIQUE (username)
);

COMMENT ON TABLE  users             IS 'Пользователи';

COMMENT ON COLUMN users.id          IS 'Идентификатор пользователя';
COMMENT ON COLUMN users.username    IS 'Логин';
COMMENT ON COLUMN users.first_name  IS 'Имя';
COMMENT ON COLUMN users.last_name   IS 'Фамилия';
COMMENT ON COLUMN users.middle_name IS 'Отчество';
COMMENT ON COLUMN users.created_at  IS 'Дата и время создания записи';
COMMENT ON COLUMN users.updated_at  IS 'Дата и время обновления записи';
COMMENT ON COLUMN users.deleted_at  IS 'Дата и время удаления записи';

CREATE TABLE subscriptions
(
    id          UUID         PRIMARY KEY    DEFAULT uuid_generate_v4(),
    title       TEXT         NOT NULL,
    description TEXT,
    start_date  TIMESTAMP    NOT NULL,
    end_date    TIMESTAMP    NOT NULL,
    user_id     UUID         NOT NULL,
    created_at  TIMESTAMP    NOT NULL       DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL       DEFAULT NOW(),
    deleted_at  TIMESTAMP                   DEFAULT NULL,

    CONSTRAINT fk_subscription_user FOREIGN KEY (user_id)
        REFERENCES users (id) ON DELETE CASCADE
);

COMMENT ON TABLE  subscriptions              IS 'Подписки пользователей';

COMMENT ON COLUMN subscriptions.id           IS 'Идентификатор подписки';
COMMENT ON COLUMN subscriptions.title        IS 'Название подписки';
COMMENT ON COLUMN subscriptions.description  IS 'Описание подписки';
COMMENT ON COLUMN subscriptions.start_date   IS 'Дата и время начала действия';
COMMENT ON COLUMN subscriptions.end_date     IS 'Дата и время окончания действия';
COMMENT ON COLUMN subscriptions.user_id      IS 'Идентификатор пользователя';
COMMENT ON COLUMN subscriptions.created_at   IS 'Дата и время создания записи';
COMMENT ON COLUMN subscriptions.updated_at   IS 'Дата и время обновления записи';
COMMENT ON COLUMN subscriptions.deleted_at   IS 'Дата и время удаления записи';