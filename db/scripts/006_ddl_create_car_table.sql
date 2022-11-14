CREATE TABLE IF NOT EXISTS car (
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL,
engine_id INT REFERENCES engine(id)
);

COMMENT ON TABLE car IS 'Данные автомобилей выставленных на продажу';
COMMENT ON COLUMN car.id IS 'Идентификатор атомобиля';
COMMENT ON COLUMN car.name IS 'Бренд и марка автомобиля';
COMMENT ON COLUMN car.engine_id IS 'Идентификатор двигателя автомобиля';