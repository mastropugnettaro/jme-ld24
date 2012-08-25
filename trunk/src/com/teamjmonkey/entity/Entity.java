package com.teamjmonkey.entity;

public enum Entity {

    TEST, MAIN_CHARACTER, BULL, STATIC_BLOCK;

    public BaseEntity createEntity() {

        BaseEntity entity;

        switch(this) {
            case TEST:
                entity = new MainCharacter();
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
            default:
                entity = new MainCharacter();
                break;
        }

        return entity;
    }
}