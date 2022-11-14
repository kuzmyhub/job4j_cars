ALTER TABLE auto_post ADD COLUMN head VARCHAR;

COMMENT ON COLUMN auto_post.head IS 'Заголовок объявления';