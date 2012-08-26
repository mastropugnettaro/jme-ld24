package com.teamjmonkey.level;

import com.jme3.scene.Node;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.appstates.LoadingScreenAppState;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.graphics.GraphicManager;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;
import com.teamjmonkey.util.Manager;
import com.teamjmonkey.util.PreloadManager;

public class LevelManager implements Manager {

    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;
    private GraphicManager graphicsManager;
    private PreloadManager preloadManager;
    private boolean stateInitialised;

    private Level currentLevel;
    private int currentIntLevel;
    private final int NUM_LEVELS;

    public LevelManager() {
        myApp = GameNameGoesHere.getApp();
        rootNode = myApp.getRootNode();
        controlManager = myApp.getControlManager();
        entityManager = myApp.getEntityManager();
        materialManager = myApp.getMaterialManager();
        physicsManager = myApp.getPhysicsManager();
        soundManager = myApp.getSoundManager();
        preloadManager = myApp.getPreloadManager();
        graphicsManager = myApp.getGraphicManager();
        stateInitialised = false;
        currentIntLevel = 1;
        NUM_LEVELS = 5;
    }

    public int getCurrentIntLevel() {
        return currentIntLevel;
    }

    public void setCurrentIntLevel(int level) {
        this.currentIntLevel = level;
    }

    // only call this once during the first ever level
    public void initialiseGameStatesOnce() {

        // Load LevelCommon

    }

    public void load(int level) {

        if (!stateInitialised) {
            initialiseGameStatesOnce();
        }

        materialManager.load(level);
        physicsManager.load(level);
        soundManager.load(level);
        graphicsManager.load(level);

        switch (level) {
            case 1:
                currentLevel = new Level1();
                break;
            case 2:
                currentLevel = new Level2();
                break;
            case 3:
                currentLevel = new Level3(); // new part of the map
                break;
            case 4:
                currentLevel = new Level4(); // new part of the map
                break;
            case 5:
                currentLevel = new Level5(); // new part of the map opens
                break;
        }
    }

    public void restartLevel() {
        cleanup();

        //this calls currentLevel.load() inside
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    public void loadNextLevel() {
        if (currentIntLevel == NUM_LEVELS) {
            currentIntLevel = 1;
        } else {
            currentIntLevel++;
        }

        cleanup();
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    public void loadPreviousLevel() {
        if (currentIntLevel == 1) {
            currentIntLevel = NUM_LEVELS;
        } else {
            currentIntLevel--;
        }

        cleanup();
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
    }

    public void cleanup() {
        materialManager.cleanup();
        physicsManager.cleanup();
        soundManager.cleanup();
        graphicsManager.cleanup();

        currentLevel.cleanup();
    }
}