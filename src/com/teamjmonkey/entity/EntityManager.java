package com.teamjmonkey.entity;

import com.jme3.scene.Spatial;

public class EntityManager {

    private MainCharacter mainCharacter;

    public BaseEntity create(Entity newEntity) {
        BaseEntity entity = newEntity.createEntity();
        entity.addMaterial();
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