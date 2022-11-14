ALTER TABLE car ADD COLUMN brand VARCHAR;
ALTER TABLE car ADD COLUMN model VARCHAR;

COMMENT ON COLUMN car.brand IS 'Бренд автомобиля';
COMMENT ON COLUMN car.model IS 'Модель автомобиля';