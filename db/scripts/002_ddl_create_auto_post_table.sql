CREATE TABLE IF NOT EXISTS auto_post(
  id SERIAL PRIMARY KEY,
  text VARCHAR NOT NULL,
  created TIMESTAMP NOT NULL,
  user_id INT NOT NULL REFERENCES auto_user(id)
);

COMMENT ON TABLE auto_post IS 'Данные размещённых объявлений о продаже автомобилей';
COMMENT ON COLUMN auto_post.id IS 'Идентификатор объявления';
COMMENT ON COLUMN auto_post.text IS 'Описание объявления от автора объявления';
COMMENT ON COLUMN auto_post.created IS 'Дата создания объявления';
COMMENT ON COLUMN auto_post.user_id IS 'Идентификатор пользовтеля создавшего объявление';