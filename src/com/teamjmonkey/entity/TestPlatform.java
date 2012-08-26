package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.teamjmonkey.graphics.Graphics;

public class TestPlatform extends BaseEntity {

    private RigidBodyControl rigidBodyControl;

    public TestPlatform () {
        super(Graphics.TEST_PLATFORM);
        spatial.setName("test");
        spatial.scale(30, 1, 30);
    }

    @Override
    protected CollisionShape getCollisionShape() {
        return createNewSphereCollisionShape();
    }

    @Override
    public void addPhysicsControl() {

        rigidBodyControl = new RigidBodyControl(0);
        spatial.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(spatial);
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
        spatial.removeControl(rigidBodyControl);
    }

    @Override
    public void finalise() {
        addPhysicsControl();
    }
}
