CREATE TABLE IF NOT EXISTS participates(
id SERIAL PRIMARY KEY,
user_id INT REFERENCES auto_user(id),
post_id INT REFERENCES auto_post(id)
);