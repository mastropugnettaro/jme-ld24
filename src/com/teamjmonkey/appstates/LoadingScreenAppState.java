package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.teamjmonkey.GameNameGoesHere;
import com.teamjmonkey.level.LevelManager;
import com.teamjmonkey.ui.LoadingScreen;
import com.teamjmonkey.ui.UIManager;
import com.teamjmonkey.util.GameState;

public class LoadingScreenAppState extends AbstractAppState {

    private AppStateManager stateManager;
    private int frameCount = 0;
    private LoadingScreen loadingScreen;
    private GameNameGoesHere myApp = GameNameGoesHere.getApp();
    private UIManager uiManager = myApp.getUIManager();
    private LevelManager levelManager = myApp.getLevelManager();

    public LoadingScreenAppState() {
        loadingScreen = new LoadingScreen();
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        this.stateManager = stateManager;
        GameState.setGameState(GameState.LOADING_LEVEL);
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {
        uiManager.getNifty().gotoScreen("end");
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    @Override
    public void update(float tpf) {

        if (frameCount == 1) {
            //load the screen
            loadingScreen.showLoadingScreen();
        } else if (frameCount == 150) { //using 150 as a debug, this is where you load the game

            //at end of loading
            stateManager.detach(this);

            levelManager.initialiseLevel();
            stateManager.attach(new GameAppState());
        }

        frameCount++;
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }
}