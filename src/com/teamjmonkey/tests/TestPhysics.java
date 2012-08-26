/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamjmonkey.tests;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;

/**
 *
 * @author Wesley
 */
public class TestPhysics extends SimpleApplication implements PhysicsCollisionListener, PhysicsTickListener {

    private BulletAppState bulletAppState;

    public static void main(String[] args) {
        new TestPhysics().start();
    }

    public void collision(PhysicsCollisionEvent event) {
        System.out.println("collision");
    }

    @Override
    public void simpleInitApp() {

        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry g = new Geometry("b", b);
        BoxCollisionShape boxCollisionShape = new BoxCollisionShape(new Vector3f(1, 1, 1));
        g.setMaterial(new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md" ));
        g.addControl(new RigidBodyControl(boxCollisionShape, 1));
        g.getControl(RigidBodyControl.class).setKinematic(true);
        bulletAppState.getPhysicsSpace().add(g);
        rootNode.attachChild(g);

        Geometry g1 = g.clone(false);
        g1.addControl(new RigidBodyControl(boxCollisionShape, 1));
        g1.getControl(RigidBodyControl.class).setKinematic(true);
        bulletAppState.getPhysicsSpace().add(g1);
        rootNode.attachChild(g1);

        bulletAppState.getPhysicsSpace().enableDebug(assetManager);

        bulletAppState.getPhysicsSpace().addCollisionListener(this);
        bulletAppState.getPhysicsSpace().addTickListener(this);
    }

    public void prePhysicsTick(PhysicsSpace space, float tpf) {
        System.out.println("pre");
    }

    public void physicsTick(PhysicsSpace space, float tpf) {
        System.out.println("post");
    }

    @Override
    public void simpleUpdate(float tpf) {
        super.simpleUpdate(tpf);

        System.out.println("simpleUpdate");
    }



}
