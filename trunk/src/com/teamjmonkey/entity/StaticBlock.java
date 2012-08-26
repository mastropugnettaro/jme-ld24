package com.teamjmonkey.entity;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.teamjmonkey.graphics.MonkeyMaterial;
import java.io.IOException;

public class StaticBlock extends BaseEntity {

    private RigidBodyControl rigidBodyControl;
    private Vector3f extents;

    public StaticBlock() {
        super();
    }

    public void createBlock (Vector3f extents) {
        this.extents = extents;
        Box b = new Box(new Vector3f(-extents.x, -extents.y, -extents.z), extents);
        Geometry box = new Geometry("block", b);
        spatial = box;
    }

    @Override
    public void finalise() {
        addMaterial();
        addPhysicsControl();
        addControl();
    }

    @Override
    protected CollisionShape getCollisionShape() {
        return createNewBoxCollisionShape();
    }

    @Override
    public void addPhysicsControl() {
        rigidBodyControl = new RigidBodyControl(getCollisionShape(), 0f);
        spatial.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(rigidBodyControl);
    }

    @Override
    public void addMaterial() {
        spatial.setMaterial(materialManager.getMaterial(MonkeyMaterial.MAIN_CHARACTER));
    }

    @Override
    public void addControl() {
    }

    @Override
    public void cleanup() {
        bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
        spatial.removeControl(RigidBodyControl.class);
        spatial = null;
    }
}