
drop table chrctr;
CREATE TABLE chrctr (
    id VARCHAR(36) PRIMARY KEY,
    user_id VARCHAR(36) NOT NULL,
    "name"  VARCHAR(36) UNIQUE NOT NULL,
    occupation VARCHAR(12) NOT NULL,
    health INT NOT NULL,
    mana INT NOT NULL,
    experience INT NOT NULL,
    "level" INT NOT NULL,
    created_at DATE NOT NULL,
    strength INT NOT NULL,
    vitality INT NOT NULL,
    dexterity INT NOT NULL,
    intelligence INT NOT NULL,
    -- inventory
    -- currently equipped items
    CONSTRAINT fk_usr_id
        FOREIGN KEY (user_id)
        REFERENCES usr(id)
);

select * from chrctr;






