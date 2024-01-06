----------------------------------
--                              --
--  CREATED BY: DANIEL ANTHONY  --
--  DATE:           12/16/2023  --
--                              --
----------------------------------

CREATE OR REPLACE PROCEDURE CRT_USR(
    P_ID VARCHAR2,
    P_USERNAME VARCHAR2,
    P_EMAIL VARCHAR2,
    P_PASSWORD VARCHAR2
) AS
BEGIN
    INSERT INTO USR (
        ID,
        USERNAME,
        EMAIL,
        PASSWORD
    ) VALUES (
        P_ID,
        P_USERNAME,
        P_EMAIL,
        P_PASSWORD
    );
END CRT_USR;
/