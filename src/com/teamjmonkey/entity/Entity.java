package com.teamjmonkey.entity;

import com.teamjmonkey.entity.food.Apple;
import com.teamjmonkey.entity.weapons.Gun;
import com.teamjmonkey.entity.weapons.Spear;
import com.teamjmonkey.entity.weapons.Sword;

public enum Entity {

    TEST_FLOOR, MAIN_CHARACTER, BULL, BLOB, ARMADILO, ELEPHANT, OCTOPUS,
        STATIC_BLOCK, SPEAR, SWORD, GUN, APPLE;

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
            case BLOB:
                entity = new CreatureBlob();
                break;
            case ARMADILO:
                entity = new CreatureArmadilo();
                break;
            case ELEPHANT:
                entity = new CreatureElephant();
                break;
            case OCTOPUS:
                entity = new CreatureOctopus();
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