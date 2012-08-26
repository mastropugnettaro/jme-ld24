package com.teamjmonkey.entity;

public enum Entity {

    TEST_FLOOR, MAIN_CHARACTER, BULL, STATIC_BLOCK, SPEAR;

    public BaseEntity createEntity() {

        BaseEntity entity;

        switch(this) {
            case TEST_FLOOR:
                entity = new TestPlatform();
                break;
            case MAIN_CHARACTER:
                entity = new MainCharacter();
                break;
            case BULL:
                entity = new Bull();
                break;
            case STATIC_BLOCK:  
                entity = new StaticBlock();
                break;
            case SPEAR:
                entity = new Spear();
                break;
            default:
                entity = new MainCharacter();
                break;
        }

        return entity;
    }
}