package com.teamjmonkey.level;

import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.entity.Bull;
import com.teamjmonkey.entity.Entity;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.entity.StaticBlock;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;

public class Level2 implements Level {

    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;
    private Camera cam;

    public Level2() {
        myApp = GameNameGoesHere.getApp();
        rootNode = myApp.getRootNode();
        controlManager = myApp.getControlManager();
        entityManager = myApp.getEntityManager();
        materialManager = myApp.getMaterialManager();
        physicsManager = myApp.getPhysicsManager();
        soundManager = myApp.getSoundManager();
        cam = myApp.getCamera();

        load();
    }

    @Override
    public void load() {
        cam.setLocation(new Vector3f(0, 0, 20));

        Bull bull = (Bull) entityManager.create(Entity.BULL);
        bull.getSpatial().addControl(controlManager.getControl(MonkeyControl.LOOK_AT));
        
        loadLights();
        rootNode.attachChild(bull.getSpatial());

        StaticBlock staticBlock = (StaticBlock) entityManager.create(Entity.STATIC_BLOCK);
        staticBlock.createBlock(new Vector3f(10, 0.5f, 10));
        staticBlock.getSpatial().move(2, 0, 0);
        staticBlock.finalise();

        rootNode.attachChild(staticBlock.getSpatial());
    }

    public void loadLights() {
        DirectionalLight sun = new DirectionalLight();
        sun.setDirection((new Vector3f(-0.5f, -0.5f, -0.5f)).normalizeLocal());
        sun.setColor(ColorRGBA.White);
        rootNode.addLight(sun);
    }

    @Override
    public void cleanup() {
        // remove any physics controls

        rootNode.detachAllChildren();
    }
}