package com.teamjmonkey.entity.weapons;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial.CullHint;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.util.Util;
import java.util.Random;

public class Gun extends WeaponEntity {

    private RigidBodyControl rigidBodyControl;
    private Random random = new Random();

    public Gun() {
        super(Graphics.GUN);
        spatial.setName("gun");
        spatial.setUserData("entity", this);
        spatial.scale(0.4f);

        animComponent = new AnimComponent(spatial, AnimType.IDLE);
    }

    @Override
    public void attack() {
        animComponent.setCurAnim(AnimType.FIRE);
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
       //  Geometry geom = Util.getGeometryFromNode(spatial);
       //  geom.getMaterial().getAdditionalRenderState().setDepthTest(false);
       //  spatial.setQueueBucket(Bucket.Translucent);
       //  spatial.setCullHint(CullHint.Never);
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