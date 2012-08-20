package com.teamjmonkey.appstates;

import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.teamjmonkey.ui.MainMenuScreen;
import com.teamjmonkey.util.GameState;

public class MainMenuAppState extends AbstractAppState {

    private MainMenuScreen mainMenuScreen;

    public MainMenuAppState() {
        mainMenuScreen = new MainMenuScreen();
    }

    @Override
    public void stateAttached(AppStateManager stateManager) {
        mainMenuScreen.goToMainMenu();
        GameState.setGameState(GameState.MAIN_SCREEN);
    }

    @Override
    public void stateDetached(AppStateManager stateManager) {

    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
    }

    @Override
    public void cleanup() {
        super.cleanup();
    }
}
