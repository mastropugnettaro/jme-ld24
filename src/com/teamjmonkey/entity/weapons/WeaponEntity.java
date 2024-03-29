package com.teamjmonkey.entity.weapons;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.export.Savable;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.animation.AnimType;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.graphics.Graphics;
import java.io.IOException;

public abstract class WeaponEntity extends BaseEntity implements Savable {

    public WeaponEntity(Graphics graphics) {
        super(graphics);
    }

    public abstract void attack();

    public void idle() {
        animComponent.setCurAnim(AnimType.IDLE);
    }

    @Override
    protected void addMaterial() {
    }

    @Override
    protected void addControl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanup() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void finalise() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void write(JmeExporter e) throws IOException {
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(this.animComponent, "animComponent", null);
    }

    @Override
    public void read(JmeImporter e) throws IOException {
        InputCapsule capsule = e.getCapsule(this);
        animComponent = (AnimComponent) capsule.readSavable("animComponent", null);
    }

    @Override
    public CollisionShape getCollisionShape() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
