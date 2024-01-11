CREATE OR REPLACE PROCEDURE crt_chrctr(
    p_id VARCHAR2,
    p_user_id VARCHAR2,
    p_name VARCHAR2,
    p_occupation VARCHAR2
) AS
    v_health INT;
    v_mana INT;
    v_strength INT;
    v_vitality INT;
    v_dexterity INT;
    v_intelligence INT;
BEGIN
    IF p_occupation = 'MONK' THEN
      v_health := 100;
      v_mana := 20;
      v_strength := 9;
      v_vitality := 100;
      v_dexterity := 0;
      v_intelligence := 0;

    ELSIF p_occupation = 'WARRIOR' THEN
      v_health := 40;
      v_mana := 20;
      v_strength := 100;
      v_vitality := 9;
      v_dexterity := 0;
      v_intelligence := 0;

    ELSIF p_occupation = 'WIZARD' THEN
      v_health := 20;
      v_mana := 100;
      v_strength := 0;
      v_vitality := 9;
      v_dexterity := 0;
      v_intelligence := 100;

    ELSIF p_occupation = 'ASSASSIN' THEN
      v_health := 40;
      v_mana := 20;
      v_strength := 0;
      v_vitality := 9;
      v_dexterity := 100;
      v_intelligence := 100;

    ELSIF p_occupation = 'HEALER' THEN
      v_health := 20;
      v_mana := 100;
      v_strength := 0;
      v_vitality := 9;
      v_dexterity := 0;
      v_intelligence := 100;

    ELSIF p_occupation = 'TAMER' THEN
      v_health := 20;
      v_mana := 100;
      v_strength := 0;
      v_vitality := 9;
      v_dexterity := 50;
      v_intelligence := 50;

    ELSE
      RAISE_APPLICATION_ERROR(-20001, 'Invalid occupation: ' || p_occupation);
    END IF;

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
      v_health,
      v_mana,
      0, -- experience
      0, -- level
      SYSDATE, -- DATE
      v_strength,
      v_vitality,
      v_dexterity,
      v_intelligence
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
