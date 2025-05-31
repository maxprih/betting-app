CREATE TABLE Balance
(
    id      SERIAL PRIMARY KEY,
    amount  INT NOT NULL default 0,
    user_id INT NOT NULL unique,
        check ( amount >= 0 )
);