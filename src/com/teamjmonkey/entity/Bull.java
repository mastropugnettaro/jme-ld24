package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.controls.AggroControl;
import com.teamjmonkey.controls.MoveRandomControl;
import com.teamjmonkey.graphics.Graphics;

public class Bull extends MovableEntity {

    public Bull() {
        super(Graphics.BULL);
        spatial.setName("enemy");

        animComponent = new AnimComponent(spatial);
    }

    @Override
    public CollisionShape getCollisionShape() {
        return new SphereCollisionShape(getExtents().z);
    }

    @Override
    public void addPhysicsControl() {
    }

    @Override
    public void addMaterial() {
    }

    @Override
    public void addControl() {
        // spatial.addControl(controlManager.getControl(MonkeyControl.LOOK_AT_ORIGIN));
    }

    @Override
    public void cleanup() {
        spatial.removeControl(MoveRandomControl.class);
        spatial.getControl(AggroControl.class).cleanup();
    }

    @Override
    public void finalise() {
        addControl();
    }
}
