CREATE TABLE IF NOT EXISTS participates(
id SERIAL PRIMARY KEY,
user_id INT REFERENCES auto_user(id),
post_id INT REFERENCES auto_post(id)
);

COMMENT ON TABLE participates IS
'Даныне о подписках зарегестрированных пользователей на объявления о продаже автомобилей';
COMMENT ON COLUMN paricipates.id IS 'Идентификатор подписки';
COMMENT ON COLUMN paricipates.user_id IS 'Идентификатор зарегистрированного пользователя';
COMMENT ON COLUMN paricipates.post_id IS 'Идентификатор объявления о продаже автомобиля';