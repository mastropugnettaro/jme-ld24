package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.graphics.MonkeyMaterial;

public class MainCharacter extends BaseEntity {

    private RigidBodyControl rigidBodyControl;

    public MainCharacter() {
        super(Graphics.MAIN_CHARACTER);
        spatial.setName("mainCharacter");
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
    public void addMaterial() {
        spatial.setMaterial(materialManager.getMaterial(MonkeyMaterial.MAIN_CHARACTER));
    }

    @Override
    public void addControl() {
        spatial.addControl(controlManager.getControl(MonkeyControl.LOOK_AT_ORIGIN));
    }

    @Override
    public void cleanup() {
        bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
        spatial.removeControl(spatial.getControl(MonkeyControl.LOOK_AT_ORIGIN.getClazz()));
    }
}
