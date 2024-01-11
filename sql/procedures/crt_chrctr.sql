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

    COMMIT;
END crt_chrctr;
/

v_user_id := null;

WITH v_user_id AS (
  SELECT '63e662b6-9610-49b9-a09b-94d9dc61bfb0' as v_user_id FROM dual
)
SELECT *
FROM chrctr, v_user_id
WHERE (
  v_user_id.v_user_id IS NULL OR user_id = v_user_id.v_user_id
) AND (
  id = 'e9376b0e-cd72-4923-aab3-abb059886266' OR "name" = 'Wormhole'
)
ORDER BY id;
FETCH FIRST ROW ONLY;

select * from usr;
select * from chrctr;
CALL crt_chrctr(
        '63e662b6-9610-49b9-a09b-94d9dc61bfb6',
        '63e662b6-9610-49b9-a09b-94d9dc61bfb0',
        'Killbox',
        'WARRIOR'
    );
