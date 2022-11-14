CREATE TABLE IF NOT EXISTS auto_user(
  id SERIAL PRIMARY KEY,
  login VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  UNIQUE (login)
);

COMMENT ON TABLE auto_user IS 'Данные зарегистрированных пользователей';
COMMENT ON COLUMN auto_user.id IS 'Идентификатор пользователя';
COMMENT ON COLUMN auto_user.login IS 'Имя пользователя';
COMMENT ON COLUMN auto_user.password IS 'Пароль пользователя';