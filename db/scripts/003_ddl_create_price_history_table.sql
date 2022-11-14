CREATE TABLE IF NOT EXISTS price_history(
   id SERIAL PRIMARY KEY,
   before BIGINT NOT NULL,
   after BIGINT NOT NULL,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   post_id INT REFERENCES auto_post(id)
);

COMMENT ON TABLE price_history IS 'Данные истории изменения стоимости автомобиля';
COMMENT ON COLUMN price_history.id IS 'Идентификатор истории изменения стоимости';
COMMENT ON COLUMN price_history.before IS 'Цена перед изменением стоимости';
COMMENT ON COLUMN price_history.after IS 'Цена после изменения стоимости';
COMMENT ON COLUMN price_history.created IS 'Дата изменения стоимости';
COMMENT ON COLUMN price_history.post_id IS 'Идентификатор объявления о продаже автомобиля';
