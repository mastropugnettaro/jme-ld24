package com.teamjmonkey.physics;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.PhysicsControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.teamjmonkey.util.Manager;
import java.util.EnumMap;
import java.util.Iterator;

public class PhysicsManager implements Manager {

    private EnumMap<MonkeyCollisionShape, CollisionShape> collisionShapeMap = new EnumMap<MonkeyCollisionShape, CollisionShape>(MonkeyCollisionShape.class);

    public void load(int level) {
        if (level == 1) {
            loadCollisionShapes(new MonkeyCollisionShape[]{MonkeyCollisionShape.CHARACTER, MonkeyCollisionShape.MEDIUM_TREE});
        }
    }

    private void loadCollisionShapes(MonkeyCollisionShape[] monkeyCollisionShapes) {
        for (MonkeyCollisionShape monkeyCollisionShape : monkeyCollisionShapes) {
            collisionShapeMap.put(monkeyCollisionShape, monkeyCollisionShape.createCollisionShape());
        }
    }

    public PhysicsControl getPhysicsControl(MonkeyCollisionShape monkeyCollisionShape) {
        RigidBodyControl rigidBodyControl = new RigidBodyControl(collisionShapeMap.get(monkeyCollisionShape), 1);
        rigidBodyControl.setKinematic(true);
        return rigidBodyControl;
    }

    public CollisionShape getCollisionShape(MonkeyCollisionShape monkeyCollisionShape) {
        return collisionShapeMap.get(monkeyCollisionShape);
    }

    public void cleanup() {
        collisionShapeMap.clear();
    }

}