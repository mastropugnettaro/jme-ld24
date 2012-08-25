package com.teamjmonkey.entity;

public class EntityManager {

    private MainCharacter mainCharacter;

    public BaseEntity create(Entity newEntity) {
        BaseEntity entity = newEntity.createEntity();
        return entity;
    }

    public MainCharacter createMainCharacter() {
        mainCharacter = (MainCharacter) create(Entity.MAIN_CHARACTER);
        mainCharacter.addMaterial();
        return mainCharacter;
    }

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }
}