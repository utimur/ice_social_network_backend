ALTER TABLE usr
    ADD COLUMN name varchar(255)
    AFTER username;

ALTER TABLE usr
    ADD COLUMN surname varchar(255)
    AFTER name;