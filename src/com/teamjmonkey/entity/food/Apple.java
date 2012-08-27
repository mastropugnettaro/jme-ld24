package com.teamjmonkey.entity.food;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.teamjmonkey.controls.FoodControl;
import com.teamjmonkey.graphics.Graphics;
import com.teamjmonkey.util.Util;
import java.util.Random;

public class Apple extends FoodEntity {

    private RigidBodyControl rigidBodyControl;
    private Random random = new Random();

    public Apple() {
        super(Graphics.APPLE);
        spatial.setName("apple");
        spatial.setUserData("entity", this);
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
         rigidBodyControl = new RigidBodyControl(1);
         rigidBodyControl.setCollisionGroup(4);
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
       // spatial.addControl(controlManager.getControl(MonkeyControl.WEAPON_FOLLOW_CAM));
       // spatial.addControl(controlManager.getControl(MonkeyControl.FOOD_THROW_CONTROL));
        spatial.addControl(new FoodControl(getColor(), 10f));
    }

    @Override
    public void cleanup() {

        spatial.removeControl(rigidBodyControl);
        bulletAppState.getPhysicsSpace().remove(rigidBodyControl);

//        spatial.getControl(MonkeyControl.FOOD_THROW_CONTROL.getClazz()).cleanup();
    //    spatial.getControl(MonkeyControl.WEAPON_FOLLOW_CAM.getClazz()).cleanup();
        spatial.removeControl(FoodControl.class);
    }

    @Override
    public void finalise() {
        addMaterial();
        addControl();
        addPhysicsControl();
    }
}
