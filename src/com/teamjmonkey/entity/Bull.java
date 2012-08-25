package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;

public class Bull extends BaseEntity {

    private RigidBodyControl rigidBodyControl;

    public Bull() {
        super(Graphics.BULL);
        spatial.setName("bull");
    }

    @Override
    protected CollisionShape getCollisionShape() {
        return createNewSphereCollisionShape();
    }

    @Override
    public void addPhysicsControl() {
        rigidBodyControl = new RigidBodyControl(getCollisionShape(), 0.2f);
        spatial.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }

    @Override
    public void addMaterial() { }

    @Override
    public void addControl() {
       // spatial.addControl(controlManager.getControl(MonkeyControl.LOOK_AT_ORIGIN));
    }

    @Override
    public void cleanup() {
        bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
        spatial.removeControl(spatial.getControl(MonkeyControl.LOOK_AT.getClazz()));
    }

    @Override
    public void finalise() {
        addPhysicsControl();
        addControl();
    }
}
