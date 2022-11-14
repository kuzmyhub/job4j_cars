ALTER TABLE auto_post ADD COLUMN photo BYTEA;

COMMENT ON COLUMN auto_post.photo IS 'Фотография автомобиля';