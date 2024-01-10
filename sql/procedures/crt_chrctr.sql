CREATE OR REPLACE PROCEDURE crt_chrctr(
    p_id VARCHAR2,
    p_user_id VARCHAR2,
    p_name VARCHAR2,
    p_occupation VARCHAR2,
    p_health INT,
    p_mana INT,
    p_experience INT,
    p_level INT,
    p_strength INT,
    p_vitality INT,
    p_dexterity INT,
    p_intelligence INT
) AS
BEGIN
  INSERT INTO chrctr(
     id,
     user_id,
     "name",
     occupation,
     health,
     mana,
     experience,
     "level",
     created_at,
     strength,
     vitality,
     dexterity,
     intelligence
  ) VALUES (
     p_id,
     p_user_id,
     p_name,
     p_occupation,
     p_health,
     p_mana,
     p_experience,
     p_level,
     SYSDATE,
     p_strength,
     p_vitality,
     p_dexterity,
     p_intelligence
  );
END crt_chrctr;
/

select * from usr;
select * from chrctr;
CALL crt_chrctr(
        'character_id_123',
        '9ef8b827-ad40-44d3-b00f-8c6066424889',
        'Warrior',
        100,  -- health
        50,   -- mana
        0,    -- experience
        1,    -- level
        SYSDATE,  -- current date and time for created_at
        20,   -- strength
        15,   -- vitality
        10,   -- dexterity
        12    -- intelligence
    );
