/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamjmonkey.level;

import com.jme3.bullet.BulletAppState;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.entity.Entity;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.entity.MainCharacter;
import com.teamjmonkey.entity.TestPlatform;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.entity.Spear;
import java.util.LinkedList;

public class Level4 implements Level {

    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;
    private Camera cam;
    private BulletAppState bulletAppState;
    private LinkedList<BaseEntity> allEntities;

    public Level4() {
        myApp = GameNameGoesHere.getApp();
        rootNode = myApp.getRootNode();
        controlManager = myApp.getControlManager();
        entityManager = myApp.getEntityManager();
        materialManager = myApp.getMaterialManager();
        physicsManager = myApp.getPhysicsManager();
        soundManager = myApp.getSoundManager();
        bulletAppState = myApp.getBulletAppState();
        allEntities = new LinkedList<BaseEntity>();
        cam = myApp.getCamera();

        load();
    }

    @Override
    public void load() {
        TestPlatform testPlatform = (TestPlatform) entityManager.create(Entity.TEST_FLOOR);
        testPlatform.finalise();
        rootNode.attachChild(testPlatform.getSpatial());
        allEntities.add(testPlatform);

        MainCharacter mainCharacter = (MainCharacter) entityManager.create(Entity.MAIN_CHARACTER);
        mainCharacter.getSpatial().move(0, 10, 0);
        mainCharacter.finalise();
        rootNode.attachChild(mainCharacter.getSpatial());
        allEntities.add(mainCharacter);

        Spear spear = (Spear) entityManager.create(Entity.SPEAR);
        spear.finalise();
        rootNode.attachChild(spear.getSpatial());
        allEntities.add(spear);
    }

    @Override
    public void cleanup() {

        for (BaseEntity baseEntity : getAllEntities()) {
            baseEntity.cleanup(); // should remove all physics and controls
            baseEntity.getSpatial().removeFromParent(); // remove from scene graph
        }

        allEntities.clear();
        allEntities = null;
    }

    public LinkedList<BaseEntity> getAllEntities() {
        return allEntities;
    }
}