CREATE TABLE IF NOT EXISTS driver (
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL,
user_id INT REFERENCES auto_user(id) unique
);

COMMENT ON TABLE driver IS 'Данные владельцев автомобилей';
COMMENT ON COLUMN driver.id IS 'Идентификатор владельца автомобля';
COMMENT ON COLUMN driver.name IS 'Имя владельца автомобиля';
COMMENT ON COLUMN driver.user_id IS 'Идентификатор зарегистрированного пользователя';