----------------------------------
--                              --
--  CREATED BY: DANIEL ANTHONY  --
--  DATE:           12/16/2023  --
--                              --
----------------------------------

CREATE OR REPLACE TYPE USR_ROW AS
    OBJECT (
        ID VARCHAR(36),
        USERNAME VARCHAR(50),
        EMAIL VARCHAR(320),
        PASSWORD VARCHAR(255)
    );

CREATE OR REPLACE TYPE USR_TAB AS TABLE OF USR_ROW;

