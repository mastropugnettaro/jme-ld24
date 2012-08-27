package com.teamjmonkey.entity.food;

import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Spatial;
import com.teamjmonkey.controls.FoodControl;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.util.Util;
import java.util.Random;

public class Apple extends FoodEntity implements PhysicsCollisionListener {

    private RigidBodyControl rigidBodyControl;
    private Random random = new Random();
    private boolean stopColliding = false;

    public Apple() {
        super(Graphics.APPLE);
        spatial.setName("apple");
        spatial.setUserData("entity", this);
        bulletAppState.getPhysicsSpace().addCollisionListener(this);
    }

    @Override
    public CollisionShape getCollisionShape() {
        return createNewSphereCollisionShape();
    }

    @Override
    public String getPicture() {
        return "Interface/appleImg.png";
    }

    public ColorRGBA getColor() {
        return (ColorRGBA) Util.getGeometryFromNode(spatial).getMaterial().getParam("Diffuse").getValue();
    }

    @Override
    public int getEnergy() {
        return 10;
    }

    @Override
    public void addPhysicsControl() {
        stopColliding = false;
        rigidBodyControl = new RigidBodyControl(1);
        rigidBodyControl.setCollisionGroup(PhysicsCollisionObject.COLLISION_GROUP_04);
        rigidBodyControl.setCollideWithGroups(PhysicsCollisionObject.COLLISION_GROUP_01 | PhysicsCollisionObject.COLLISION_GROUP_03);
        spatial.addControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().add(spatial);
    }

    @Override
    public void addMaterial() {

        // get a random material from the code
        String materials[] = new String[]{"Models/Food/greenAppleMaterial.j3m", "Models/Food/redAppleMaterial.j3m", "Models/Food/blueAppleMaterial.j3m"};

        String materialSelected = materials[random.nextInt(3)];
        spatial.setMaterial(assetManager.loadMaterial(materialSelected));
    }

    @Override
    public void addControl() {
        spatial.addControl(controlManager.getControl(MonkeyControl.WEAPON_FOLLOW_CAM));
        spatial.addControl(controlManager.getControl(MonkeyControl.FOOD_THROW_CONTROL));
        spatial.addControl(new FoodControl(getColor(), 10f));
    }

    @Override
    public void cleanup() {



        spatial.removeControl(FoodControl.class);
    }

    @Override
    public void finalise() {
        addMaterial();
        //addControl();
        addPhysicsControl();
    }

    public void collision(PhysicsCollisionEvent event) {

        //  System.out.println("ger");

        if (spatial == null || event.getNodeA() == null || event.getNodeB() == null || stopColliding) {
            return;
        }

        Spatial nodeA = event.getNodeA();
        Spatial nodeB = event.getNodeB();

        if (nodeA.equals(spatial) && nodeB.getName().equals("mainCharacter")
                || nodeB.equals(spatial) && nodeA.getName().equals("mainCharacter")) {

            //successful collision
            //rigidBodyControl.setEnabled(false);

            System.out.println("gerrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
            stopColliding = true;

            // spatial.getControl(MonkeyControl.FOOD_THROW_CONTROL.getClazz()).cleanup();
            // spatial.getControl(MonkeyControl.WEAPON_FOLLOW_CAM.getClazz()).cleanup();
            addControl();

            spatial.removeControl(rigidBodyControl);
            bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
        }

        /*
        PhysicsCollisionObject object = null;
        Spatial target = null;
        if (event.getNodeA().equals(spatial)) {
        object = event.getObjectA();
        target = event.getNodeB();
        }
        if (event.getNodeB().equals(spatial)) {
        object = event.getObjectB();
        target = event.getNodeA();
        }

        if (object != null && target != null && object.equals(losGhost)) {
        hasLosAggro = true;
        losTarget = target;
        } else if (object != null && target != null && object.equals(attackGhost)) {
        hasAttackAggro = true;
        attackTarget = target;
        }
         *
         */


    }
}
