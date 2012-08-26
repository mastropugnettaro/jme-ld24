/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamjmonkey.physics;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;

/**
 *
 * @author Wesley
 */
public class MainCharacterPhysics extends RigidBodyControl implements PhysicsCollisionListener {

    public MainCharacterPhysics(CollisionShape collisionShape, float mass) {
        super(collisionShape, mass);

        setSleepingThresholds(0.0f, 0.0f);
        setAngularFactor(0.0f);
    }

    @Override
    public void update(float tpf) {
        super.update(tpf);
    }

    public void collision(PhysicsCollisionEvent event) {
    }
}