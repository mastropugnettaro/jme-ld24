/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.teamjmonkey.level;

import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
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
import com.teamjmonkey.ai.aggro.AggroBehaviorStare;
import com.teamjmonkey.ai.areas.WalkableArea;
import com.teamjmonkey.ai.areas.WalkableCircle;
import com.teamjmonkey.ai.areas.WalkableRectangle;
import com.teamjmonkey.controls.AggroControl;
import com.teamjmonkey.controls.MoveRandomControl;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.entity.Bull;
import java.util.LinkedList;

public class Level1 implements Level {

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

    public Level1() {
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

        WalkableArea field = new WalkableRectangle(-156f, -156f, 312f, 312f);
        WalkableArea midCircle = new WalkableCircle(0f, 0f, 20f);
        for (int i = 0; i < 30; i++) {
            Bull bull = (Bull) entityManager.create(Entity.BULL);
            bull.getSpatial().addControl(new MoveRandomControl(bull, field));
            bull.getSpatial().addControl(new AggroControl(bull, 20f, 5f, new AggroBehaviorStare(0.5f), null));
            bull.getSpatial().setLocalTranslation(field.getRandomPointInside());
            bull.finalise();
            rootNode.attachChild(bull.getSpatial());
            allEntities.add(bull);
        }

        for (int i = 0; i < 5; i++) {
            Bull bull = (Bull) entityManager.create(Entity.BULL);
            bull.getSpatial().addControl(new MoveRandomControl(bull, midCircle));
            bull.getSpatial().addControl(new AggroControl(bull, 20f, 5f, new AggroBehaviorStare(0.5f), null));
            bull.getSpatial().setLocalTranslation(midCircle.getRandomPointInside());
            bull.finalise();
            rootNode.attachChild(bull.getSpatial());
            allEntities.add(bull);
        }

        MainCharacter mainCharacter = (MainCharacter) entityManager.create(Entity.MAIN_CHARACTER);
        mainCharacter.getSpatial().move(0, 10, 0);
        mainCharacter.finalise();
        rootNode.attachChild(mainCharacter.getSpatial());
        allEntities.add(mainCharacter);
    }

    @Override
    public void cleanup() {

        for (BaseEntity baseEntity : getAllEntities()) {
            baseEntity.cleanup(); // should remove all physics and controls
            rootNode.detachChild(baseEntity.getSpatial()); // remove from scene graph
        }

        allEntities.clear();
        allEntities = null;
    }

    public LinkedList<BaseEntity> getAllEntities() {
        return allEntities;
    }
}
