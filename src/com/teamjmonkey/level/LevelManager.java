package com.teamjmonkey.level;

import com.jme3.scene.Node;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.appstates.LoadingScreenAppState;
import com.teamjmonkey.controls.ControlManager;
import com.teamjmonkey.entity.EntityManager;
import com.teamjmonkey.graphics.MaterialManager;
import com.teamjmonkey.physics.PhysicsManager;
import com.teamjmonkey.sound.SoundManager;
import com.teamjmonkey.util.Manager;

public class LevelManager implements Manager {

    private GameNameGoesHere myApp;
    private Node rootNode;
    private ControlManager controlManager;
    private EntityManager entityManager;
    private MaterialManager materialManager;
    private PhysicsManager physicsManager;
    private SoundManager soundManager;
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
        stateInitialised = false;
        currentIntLevel = 1;
        NUM_LEVELS = 2;
    }

    public int getCurrentIntLevel() {
        return currentIntLevel;
    }

    public void setCurrentIntLevel(int level) {
        this.currentIntLevel = level;
    }

    // only call this once during the first ever level
    public void initialiseGameStatesOnce() {
    }

    public void load(int level) {

        if (!stateInitialised) {
            initialiseGameStatesOnce();
        }

        materialManager.load(level);
        physicsManager.load(level);
        
        soundManager.load(level);

        switch (level) {
            case 1:
                currentLevel = new Level1();
                break;
            case 2:
                currentLevel = new Level2();
                break;
        }
    }

    public void restartLevel() {

        currentLevel.cleanup();

        //this calls currentLevel.load() inside
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
        currentLevel.load();
    }

    public void loadNextLevel() {
        if (currentIntLevel == NUM_LEVELS) {
            currentIntLevel = 1;
        } else {
            currentIntLevel++;
        }

        currentLevel.cleanup();
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
        load(currentIntLevel);
    }

    public void loadPreviousLevel() {
        if (currentIntLevel == 1) {
            currentIntLevel = NUM_LEVELS;
        } else {
            currentIntLevel--;
        }

        currentLevel.cleanup();
        myApp.getStateManager().attach(myApp.getMonkeyAppStateManager().getAppState(LoadingScreenAppState.class));
        load(currentIntLevel);
    }

    public void cleanup() {
        materialManager.cleanup();
        physicsManager.cleanup();
        soundManager.cleanup();

        currentLevel.cleanup();
    }
}