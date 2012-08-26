package com.teamjmonkey.entity;

public class EntityManager {

    public BaseEntity create(Entity newEntity) {
        BaseEntity entity = newEntity.createEntity();
        return entity;
    }
}