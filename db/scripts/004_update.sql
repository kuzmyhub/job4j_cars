INSERT INTO auto_user (login, password) VALUES ('Ivanov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Petrov', 'root');
INSERT INTO auto_user (login, password) VALUES ('Sidorov', 'root');

CREATE TABLE price_history(
   id SERIAL PRIMARY KEY,
   before BIGINT NOT NULL,
   after BIGINT NOT NULL,
   created TIMESTAMP WITHOUT TIME ZONE DEFAULT now(),
   auto_post_id INT REFETENCES auto_post(id)
);