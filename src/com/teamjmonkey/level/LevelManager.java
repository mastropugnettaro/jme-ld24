package com.teamjmonkey.level;

import com.jme3.app.FlyCamAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.controls.BaseControl;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.controls.MonkeyControl;
import com.teamjmonkey.entity.BaseEntity;
import com.teamjmonkey.entity.Entity;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.entity.MainCharacter;
import com.teamjmonkey.graphics.MaterialManager;

public class LevelManager {

    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;

    public LevelManager() {
        myApp = GameNameGoesHere.getApp();
        rootNode = myApp.getRootNode();
        controlManager = myApp.getControlManager();
        entityManager = myApp.getEntityManager();
        materialManager = myApp.getMaterialManager();
    }

    public void initialiseLevel() {

        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry g = new Geometry("box", b);
        g.setMaterial(new Material(myApp.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"));

        g.move(2, 0, 0);
        myApp.getRootNode().attachChild(g);

        BaseControl control = (BaseControl) controlManager.getControl(MonkeyControl.LOOK_AT_ORIGIN);
        rootNode.addControl(control);

        int level = 1;
        materialManager.load(level);

        BaseEntity create = entityManager.create(Entity.TEST);
        MainCharacter createMainCharacter = entityManager.createMainCharacter();

        createMainCharacter.getSpatial().move(-3, 0, 0);

        rootNode.attachChild(create.getSpatial());
        rootNode.attachChild(createMainCharacter.getSpatial());
    }
}