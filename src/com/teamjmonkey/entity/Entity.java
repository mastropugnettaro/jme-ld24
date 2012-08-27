package com.teamjmonkey.entity;

import com.teamjmonkey.entity.food.Apple;
import com.teamjmonkey.entity.weapons.Gun;
import com.teamjmonkey.entity.weapons.Spear;
import com.teamjmonkey.entity.weapons.Sword;
import com.teamjmonkey.graphics.Graphics;

public enum Entity {

    TEST_FLOOR, MAIN_CHARACTER, BULL, CREATURE_BLOB, CREATURE_ARMADILO,
    CREATURE_ELEPHANT, CREATURE_OCTOPUS, ENEMY_BLOB, ENEMY_ARMADILO,
    ENEMY_ELEPHANT, STATIC_BLOCK, SPEAR, SWORD, GUN, APPLE;

    public BaseEntity createEntity() {

        BaseEntity entity;

        switch (this) {
            case TEST_FLOOR:
                entity = new TestPlatform();
                break;
            case MAIN_CHARACTER:
                entity = new MainCharacter();
                break;
            case BULL:
                entity = new Bull();
                break;
            case CREATURE_BLOB:
                entity = new CreatureBlob();
                break;
            case CREATURE_ARMADILO:
                entity = new CreatureArmadilo();
                break;
            case CREATURE_ELEPHANT:
                entity = new CreatureElephant();
                break;
            case CREATURE_OCTOPUS:
                entity = new CreatureOctopus();
                break;
            case ENEMY_BLOB:
                entity = new Enemy(Graphics.BLOB);
                break;
            case ENEMY_ARMADILO:
                entity = new Enemy(Graphics.ARMADILO);
                break;
            case ENEMY_ELEPHANT:
                entity = new Enemy(Graphics.ELEPHANT);
                break;
            case STATIC_BLOCK:
                entity = new StaticBlock();
                break;
            case SPEAR:
                entity = new Spear();
                break;
            case SWORD:
                entity = new Sword();
                break;
            case GUN:
                entity = new Gun();
                break;
            case APPLE:
                entity = new Apple();
                break;
            default:
                entity = new MainCharacter();
                break;
        }

        return entity;
    }
}