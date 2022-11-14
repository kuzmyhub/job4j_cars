ALTER TABLE car ADD COLUMN release INT;
ALTER TABLE car ADD COLUMN shape VARCHAR;
ALTER TABLE car ADD COLUMN drive VARCHAR;
ALTER TABLE car ADD COLUMN gsb VARCHAR;
ALTER TABLE car ADD COLUMN color VARCHAR;
ALTER TABLE car ADD COLUMN mileage INT;

COMMENT ON COLUMN car.release IS 'Год выпуска автомобиля';
COMMENT ON COLUMN car.shape IS 'Форма кузова автомобиля';
COMMENT ON COLUMN car.drive IS 'Привод автомобиля';
COMMENT ON COLUMN car.gsb IS 'Тип коробки переключения передач автомобиля';
COMMENT ON COLUMN car.color IS 'Цвет автомобиля';
COMMENT ON COLUMN car.mileage IS 'Пробег автомобиля';


