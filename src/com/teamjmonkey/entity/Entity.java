package com.teamjmonkey.entity;

public enum Entity {

    TEST, MAIN_CHARACTER;

    public BaseEntity createEntity() {

        BaseEntity entity;
        
        switch(this) {
            case TEST:
                entity = new MainCharacter();
                break;
            case MAIN_CHARACTER:
                entity = new MainCharacter();
                break;
            default:
                entity = new MainCharacter();
                break;
        }

        return entity;
    }
}