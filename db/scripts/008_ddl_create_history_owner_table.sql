CREATE TABLE IF NOT EXISTS history_owner (
id SERIAL PRIMARY KEY,
car_id INT REFERENCES car(id),
driver_id INT REFERENCES driver(id),
startAt TIMESTAMP WITHOUT TIME ZONE,
andAt TIMESTAMP WITHOUT TIME ZONE
);

COMMENT ON TABLE history_owner IS 'Данные об истоии владения автомобилями';
COMMENT ON COLUMN history_owner.id IS 'Идентификатор истории владения';
COMMENT ON COLUMN history_owner.car_id IS 'Идентификатор автомобиля выставленного на продажу';
COMMENT ON COLUMN history_owner.driver_id IS 'Идентификатор данных о владельце автомобиля';
COMMENT ON COLUMN history_owner.startAt IS 'Дата покупки автомобиля';
COMMENT ON COLUMN history_owner.andAt IS 'Дата продажи автомобиля';