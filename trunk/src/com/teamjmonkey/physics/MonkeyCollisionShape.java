package com.teamjmonkey.physics;

import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.math.Vector3f;

public enum MonkeyCollisionShape {

    CHARACTER, SMALL_TREE, MEDIUM_TREE, SPHERE_ENEMY;

    public CollisionShape createCollisionShape() {

        CollisionShape collisionShape;
        switch(this) {
            case CHARACTER:
                collisionShape = new BoxCollisionShape(new Vector3f(1, 2, 1));
                break;
            case SMALL_TREE:
                collisionShape = new BoxCollisionShape(new Vector3f(1, 1, 1));
                break;
            case MEDIUM_TREE:
                collisionShape = new BoxCollisionShape(new Vector3f(2, 2, 2));
                break;
            case SPHERE_ENEMY:
                collisionShape = new SphereCollisionShape(0.5f);
                break;
            default:
                collisionShape = new BoxCollisionShape(new Vector3f(1, 1, 1));
                break;
        }

        return collisionShape;
    }
}