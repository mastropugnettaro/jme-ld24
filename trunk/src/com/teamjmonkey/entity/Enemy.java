package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.controls.AggroControl;
import com.teamjmonkey.controls.MoveRandomControl;
import com.teamjmonkey.graphics.Graphics;

public class Enemy extends MovableEntity {

    public Enemy(Graphics graphics) {
        super(graphics);
        spatial.setName("enemy");

        animComponent = new AnimComponent(spatial);
    }

    @Override
    public void addControl() {
    }

    @Override
    public void addPhysicsControl() {
    }

    @Override
    public void addMaterial() {
    }

    @Override
    public CollisionShape getCollisionShape() {
        return new SphereCollisionShape(getExtents().z);
    }

    @Override
    public void cleanup() {
        spatial.removeControl(MoveRandomControl.class);
        spatial.getControl(AggroControl.class).cleanup();
        spatial.removeControl(AggroControl.class);
    }

    @Override
    public void finalise() {
        addControl();
    }

    @Override
    public void idleAnim() {
        animComponent.setCurAnim(AnimType.IDLE);
    }

    @Override
    public void moveAnim() {
        animComponent.setCurAnim(AnimType.WALK);
    }

    @Override
    public void jumpAnim() {
        animComponent.setCurAnim(AnimType.JUMP);
    }

    @Override
    public void attackAnim() {
        animComponent.setCurAnim(AnimType.ATTACK);
    }
}
