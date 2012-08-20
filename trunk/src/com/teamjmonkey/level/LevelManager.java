package com.teamjmonkey.level;

import com.jme3.app.FlyCamAppState;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.teamjmonkey.GameNameGoesHere;

public class LevelManager {

    private GameNameGoesHere myApp;

    public LevelManager() {
        myApp = GameNameGoesHere.getApp();
    }

    public void initialiseLevel() {

        Box b = new Box(Vector3f.ZERO, 1, 1, 1);
        Geometry g = new Geometry("box", b);
        g.setMaterial(new Material(myApp.getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md"));

        myApp.getRootNode().attachChild(g);

        myApp.getStateManager().attach(new FlyCamAppState());
    }
}