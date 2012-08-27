package com.teamjmonkey.entity.weapons;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;
import java.util.Random;

public class Spear extends WeaponEntity {

    private RigidBodyControl rigidBodyControl;
    private Random random = new Random();

    public Spear() {
        super(Graphics.SPEAR);
        spatial.setName("spear");
        spatial.setUserData("entity", this);

        animComponent = new AnimComponent(spatial, AnimType.IDLE);
    }

    @Override
    public void attack() {
        animComponent.setCurAnim(AnimType.ATTACK_STAB);
    }

    @Override
    public CollisionShape getCollisionShape() {
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
        // Geometry geom = Util.getGeometryFromNode(spatial);
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