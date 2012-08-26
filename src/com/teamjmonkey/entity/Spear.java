package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.scene.Geometry;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.util.Util;

public class Spear extends BaseEntity {

    private RigidBodyControl rigidBodyControl;

    public Spear() {
        super(Graphics.SPEAR);
        spatial.setName("spear");
    }

    @Override
    protected CollisionShape getCollisionShape() {
        return createNewSphereCollisionShape();
    }

    @Override
    public void addPhysicsControl() {
        // RigidBodyControl control = new RigidBodyControl(0);
        // spatial.addControl(control);
        // bulletAppState.getPhysicsSpace().add(spatial);
    }

    @Override
    public void addMaterial() {
        Geometry geom = Util.getGeometryFromNode(spatial);
        // geom.getMaterial().getAdditionalRenderState().setDepthTest(false);
        // spatial.setQueueBucket(Bucket.Translucent);
    }

    @Override
    public void addControl() {
        spatial.addControl(controlManager.getControl(MonkeyControl.WEAPON_FOLLOW_CAM));
        spatial.addControl(controlManager.getControl(MonkeyControl.WEAPON_ATTACK_CONTROL));
    }

    @Override
    public void cleanup() {
        //bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
        spatial.getControl(MonkeyControl.WEAPON_ATTACK_CONTROL.getClazz()).cleanup();
        spatial.getControl(MonkeyControl.WEAPON_FOLLOW_CAM.getClazz()).cleanup();
    }

    @Override
    public void finalise() {
        addMaterial();
        addControl();
        addPhysicsControl();
    }
}