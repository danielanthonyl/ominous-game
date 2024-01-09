----------------------------------
--                              --
--  CREATED BY: DANIEL ANTHONY  --
--  DATE:           12/16/2023  --
--                              --
----------------------------------

drop table usr;
CREATE TABLE USR (
    ID VARCHAR(36) PRIMARY KEY,
    USERNAME VARCHAR(50) UNIQUE,
    EMAIL VARCHAR(320) UNIQUE,
    PASSWORD VARCHAR(255)
);


