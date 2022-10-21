CREATE TABLE auto_user(
  id SERIAL PRIMARY KEY,
  login VARCHAR NOT NULL,
  password VARCHAR NOT NULL,
  UNIQUE (login)
);

CREATE TABLE auto_post(
  id SERIAL PRIMARY KEY,
  text VARCHAR NOT NULL,
  created TIMESTAMP NOT NULL,
  auto_user_id INT NOT NULL references auto_user(id)
);