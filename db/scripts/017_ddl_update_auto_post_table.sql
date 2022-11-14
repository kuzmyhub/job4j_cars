ALTER TABLE auto_post ADD COLUMN sold BOOLEAN;

COMMENT ON COLUMN auto_post.sold IS 'Статус продажи объявления';