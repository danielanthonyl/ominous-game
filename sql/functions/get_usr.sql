CREATE OR REPLACE FUNCTION GET_USR(
    P_ID VARCHAR2 DEFAULT NULL,
    P_USRNM VARCHAR2 DEFAULT NULL,
    P_EMAIL VARCHAR2 DEFAULT NULL,
    P_PASSWORD VARCHAR2 DEFAULT NULL
) RETURN USR_TAB PIPELINED AS
BEGIN
    FOR USR_REC IN(
        SELECT
            *
        FROM
            USR
        WHERE
          id = p_id
          or
          username = p_usrnm
          or
          email = p_email
           -- or (p_email is null
           -- and email = p_email)
           -- or (p_usrnm is null
           -- and username = p_usrnm)
    ) LOOP
        PIPE ROW(USR_ROW(USR_REC.ID, USR_REC.USERNAME, USR_REC.EMAIL, USR_REC.PASSWORD));
    END LOOP;

    RETURN;
END GET_USR;
/
