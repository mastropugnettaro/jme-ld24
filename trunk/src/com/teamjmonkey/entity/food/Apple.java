package com.teamjmonkey.entity.food;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.math.ColorRGBA;
import com.teamjmonkey.controls.FoodControl;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.graphics.Graphics;
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
    public void addPhysicsControl() {
        // RigidBodyControl control = new RigidBodyControl(0);
        // spatial.addControl(control);
        // bulletAppState.getPhysicsSpace().add(spatial);
    }
    
    @Override
    public void addMaterial() {
    }
    
    @Override
    public void addControl() {
        spatial.addControl(controlManager.getControl(MonkeyControl.WEAPON_FOLLOW_CAM));
        spatial.addControl(controlManager.getControl(MonkeyControl.FOOD_THROW_CONTROL));
        spatial.addControl(new FoodControl(ColorRGBA.Green, 10f));
    }
    
    @Override
    public void cleanup() {
        //bulletAppState.getPhysicsSpace().remove(rigidBodyControl);
        spatial.getControl(MonkeyControl.FOOD_THROW_CONTROL.getClazz()).cleanup();
        spatial.getControl(MonkeyControl.WEAPON_FOLLOW_CAM.getClazz()).cleanup();
    }
    
    @Override
    public void finalise() {
        addMaterial();
        addControl();
        addPhysicsControl();
    }
}
