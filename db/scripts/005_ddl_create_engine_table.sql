CREATE TABLE IF NOT EXISTS engine (
id SERIAL PRIMARY KEY,
name VARCHAR NOT NULL
);

COMMENT ON TABLE engine IS 'Данные о типе двигателя';
COMMENT ON COLUMN engine.id IS 'Идентификатор типа двигателя';
COMMENT ON COLUMN engine.name IS 'Наименование типа двигателя';