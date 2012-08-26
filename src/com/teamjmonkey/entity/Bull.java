package com.teamjmonkey.entity;

import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.GhostControl;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.teamjmonkey.animation.AnimComponent;
import com.teamjmonkey.controls.AggroControl;
import com.teamjmonkey.graphics.Graphics;

public class Bull extends BaseEntity {

    private GhostControl ghostControl;

    public Bull() {
        super(Graphics.BULL);
        spatial.setName("enemy");

        animComponent = new AnimComponent(spatial);

        //this to to align the controls better
        float getYExtent = getExtents().getY();
        Spatial tempSpatial = spatial;
        tempSpatial.move(0, -getYExtent, 0);
        spatial = new Node("enemy");
        ((Node) spatial).attachChild(tempSpatial);

        Node tempNode = (Node) spatial;
        tempNode.move(0, getYExtent, 0);
        addPhysicsControl();

        ghostControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_02);
        ghostControl.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_03);

        spatial = new Node("enemy");
        ((Node) spatial).attachChild(tempNode);
    }

    @Override
    protected CollisionShape getCollisionShape() {
        return new SphereCollisionShape(getExtents().z);
    }

    @Override
    public void addPhysicsControl() {
        ghostControl = new GhostControl(getCollisionShape());
        spatial.addControl(ghostControl);
        bulletAppState.getPhysicsSpace().add(ghostControl);
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
        //bulletAppState.getPhysicsSpace().remove(characterControl);
        //spatial.removeControl(characterControl);

        //remove ghost controls
        bulletAppState.getPhysicsSpace().remove(ghostControl);
        //spatial.removeControl(ghostControl);

        ((Node)spatial).getChild(0).removeControl(ghostControl);

        spatial.getControl(AggroControl.class).cleanup();
        //spatial.removeControl(AggroControl.class);
    }

    @Override
    public void finalise() {
        addControl();
    }
}
