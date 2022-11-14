ALTER TABLE auto_post ADD COLUMN car_id INT REFERENCES car(id) UNIQUE;

COMMENT ON COLUMN auto_post.car_id IS 'Идентификатор автомобиля выставленного на продажу';