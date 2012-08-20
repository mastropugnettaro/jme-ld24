package com.teamjmonkey;

import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.teamjmonkey.level.LevelManager;
import com.teamjmonkey.ui.UIManager;

public class GameNameGoesHere extends SimpleApplication {

    private static GameNameGoesHere thisApp;
    private UIManager uiManager;
    private LevelManager levelManager;

    public GameNameGoesHere() {
        super(new StatsAppState());
    }

    @Override
    public void simpleInitApp() {
        thisApp = this;

        // load needed managers now
        uiManager = new UIManager();
        levelManager = new LevelManager();

        uiManager.showMainMenu();
    }

    public static GameNameGoesHere getApp() {
        return thisApp;
    }

    public UIManager getUIManager() {
        return uiManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }
}